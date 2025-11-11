package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.BookingDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.BookingRequest;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.GuestsDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.*;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.enums.BookingStatus;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.enums.Role;
import org.example.tutorial_2_homework.manish_airbnb_clone.exception.ResourceNotFoundException;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {


    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;
    private final GuestRepository guestRepository;

    @Override
    @Transactional
    public BookingDto initialiseBooking(BookingRequest bookingRequest) {
        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId()).orElseThrow(() -> new ResourceNotFoundException("Could not find hotels with id : " + bookingRequest.getHotelId()));
        Room room = roomRepository.findById(bookingRequest.getRoomId()).orElseThrow(() -> new ResourceNotFoundException("Could not find Rooms with id : " + bookingRequest.getRoomId()));


        LocalDate checkInDate = bookingRequest.getCheckInDate();
        LocalDate checkOutDate = bookingRequest.getCheckOutDate();
        long daysCount = ChronoUnit.DAYS.between(checkInDate, checkOutDate) + 1;
        List<Inventory> inventoryList = inventoryRepository.findAllAndLockAvailableInventory(room.getId(), checkInDate, checkOutDate, bookingRequest.getRoomsCount());

        if (inventoryList.size() != daysCount) {
            throw new IllegalStateException("Room is Not available anymore");
        }
        //Reserve rooms / update the booked count of inventories
        for (Inventory inventory : inventoryList) {
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
        }

        inventoryRepository.saveAll(inventoryList);

        //Dummy User


        //create the booking

        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.RESERVED)
                .hotel(hotel)
                .room(room)
                .checkInDate(checkInDate)
                .checkOutDate(checkOutDate)
                .user(getCurrentUser())
                .roomsCount(bookingRequest.getRoomsCount())
                .amount(BigDecimal.valueOf(666.0))
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuests(long bookingId, List<GuestsDto> guestsDtoList) {

        log.info("Adding Guests with booking id : " + bookingId);

        Booking booking =
                bookingRepository.findById(bookingId).orElseThrow(() -> new ResourceNotFoundException("Could not find Booking Id :" + bookingId));
        if (hasBookingExpired(booking))
            throw new IllegalStateException("Booking time of 10  minutes has expired. Can not add Guests .");
        if (booking.getBookingStatus() != BookingStatus.RESERVED)
            throw new IllegalStateException("Booking is not under RESERVED State. Cannot add Guests");


        for (GuestsDto guestsDto : guestsDtoList) {
            Guest guest = modelMapper.map(guestsDto, Guest.class);
            guest.setUser(getCurrentUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }
        booking.setBookingStatus(BookingStatus.GUESTS_ADDED);
        booking = bookingRepository.save(booking);

        return modelMapper.map(booking, BookingDto.class);
    }

    public Boolean hasBookingExpired(Booking booking) {
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrentUser() {
        User users = new User(2L, "email", "password", "Manish",
                new HashSet<>(Arrays.asList(Role.GUEST, Role.HOTEL_MANAGER))
        );
        return users;
    }
}
