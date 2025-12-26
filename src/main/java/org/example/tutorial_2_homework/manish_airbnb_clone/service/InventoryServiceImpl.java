package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.*;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Inventory;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Room;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.UserEntity;
import org.example.tutorial_2_homework.manish_airbnb_clone.exception.ResourceNotFoundException;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.HotelMinPriceRepository;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.InventoryRepository;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static org.example.tutorial_2_homework.manish_airbnb_clone.util.AppUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final ModelMapper modelMapper;
    private final HotelMinPriceRepository hotelMinPriceRepository;
    private final InventoryRepository inventoryRepository;
    private final RoomRepository roomRepository;

    @Override
    public void initializeRoomForAYear(Room room) {
        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusYears(1);
        for (; !today.isAfter(endDate); today = today.plusDays(1)) {
            Inventory inventory = Inventory.builder()
                    .hotel(room.getHotel())
                    .room(room)
                    .bookedCount(0)
                    .reservedCount(0)
                    .city(room.getHotel().getCity())
                    .date(today)
                    .price(room.getBasePrice())
                    .surgeFactor(BigDecimal.ONE)
                    .totalCount(room.getTotalCount())
                    .closed(false)
                    .build();
            inventoryRepository.save(inventory);
        }
    }

    @Override
    public void deleteAllInventories(Room room) {
        inventoryRepository.deleteByRoom(room);
    }

    @Override
    public Page<HotelPriceDto> searchHotels(HotelSearchRequest hotels) {
        String city = hotels.getCity();
        int pageNumber = hotels.getPageNumber();
        int roomsCount = hotels.getRoomsCount();
        int pageSize = hotels.getPageSize();
        LocalDate startDate = hotels.getStartDate();
        LocalDate endDate = hotels.getEndDate();
        long dateCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<HotelPriceDto> hotelPage =
                hotelMinPriceRepository.findHotelsWithAvailableInventory(city,
                        startDate, endDate, pageable);

        return hotelPage;    //.map((element) -> modelMapper.map(element, HotelPriceDto.class));
    }


    @Override
    public Page sortRooms(Pageable pageable, RoomSearchRequestDto room) {
        String city = room.getCity();
        int pageNumber = room.getPageNumber();
        int pageSize = room.getPageSize();
        LocalDate startDate = room.getStartDate();
        LocalDate endDate = room.getEndDate();

        BigDecimal price = room.getBasePrice();
        long dateCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        Pageable pageable1 = PageRequest.of(pageNumber, pageSize);
        Page<Room> roomPage = inventoryRepository.findRoomsWithAvailableInventory(city,
                startDate, endDate, dateCount, price, pageable1);

        return roomPage.map((element) -> modelMapper.map(element, RoomDto.class));
    }

    @Override
    public List<InventoryDto> getAllInventoryByRoom(Long roomId) {

        log.info("Getting inventory for room id: {}", roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));
        UserEntity userEntity = getCurrentUser();
        try {
            if (!userEntity.equals(room.getHotel().getOwner()))
                throw new AccessDeniedException("You are not authorized to view the inventory of this room.");
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }

        return inventoryRepository.findByRoomOrderByDate(room)
                .stream()
                .map((element) -> modelMapper.map(element, InventoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryDto) {
        LocalDate startDate = updateInventoryDto.getStartDate();
        LocalDate endDate = updateInventoryDto.getEndDate();
        BigDecimal surgeFactor = updateInventoryDto.getSurgeFactor();
        Boolean closed = updateInventoryDto.getClosed();

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));
        UserEntity userEntity = getCurrentUser();
        try {
            if (!userEntity.equals(room.getHotel().getOwner()))
                throw new AccessDeniedException("You are not authorized to update the inventory of this room.");
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
        log.info("Updating inventory for room id: {} for date Range {}  -  {}", roomId, startDate, endDate);
        log.info("Locking inventory records for update...");
        inventoryRepository.getInventoryAndLockBeforeUpdate(roomId, startDate, endDate);
        inventoryRepository.updateInventory(roomId, startDate, endDate, closed, surgeFactor);
        return null;
    }


}


