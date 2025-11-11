package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.HotelDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.HotelSearchRequest;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.RoomDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.RoomSearchRequestDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Hotel;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Inventory;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Room;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {
    private final ModelMapper modelMapper;

    private final InventoryRepository inventoryRepository;

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
    public Page searchHotels(HotelSearchRequest hotels) {
        String city = hotels.getCity();
        int pageNumber = hotels.getPageNumber();
        int roomsCount = hotels.getRoomsCount();
        int pageSize = hotels.getPageSize();
        LocalDate startDate = hotels.getStartDate();
        LocalDate endDate = hotels.getEndDate();
        long dateCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<Hotel> hotelPage =
                inventoryRepository.findHotelsWithAvailableInventory(city,
                        startDate, endDate, roomsCount, dateCount, pageable);

        return hotelPage.map((element) -> modelMapper.map(element, HotelDto.class));
    }



//    @Override
//    public Page sortRooms(Pageable pageable, RoomSearchRequestDto room) {
//        String city = room.getCity();
//        int pageNumber = room.getPageNumber();
//        int pageSize = room.getPageSize();
//        LocalDate startDate = room.getStartDate();
//        LocalDate endDate = room.getEndDate();
//
//        BigDecimal price = room.getBasePrice();
//        long dateCount = ChronoUnit.DAYS.between(startDate, endDate) + 1;
//        Pageable pageable1 = PageRequest.of(pageNumber, pageSize);
//        Page<Room> roomPage =inventoryRepository.findRoomsWithAvailableInventory(city,
//                startDate,endDate,dateCount,price,pageable1);
//
//        return roomPage.map((element) -> modelMapper.map(element, RoomDto.class));
//    }


        public Page<RoomDto> sortRooms(Pageable pageable, RoomSearchRequestDto roomSearchRequestDto) {
            String sortBy = roomSearchRequestDto.getSortBy();
            BigDecimal maxPrice = roomSearchRequestDto.getMaxPrice();

            // Build dynamic sort
            Sort sort = Sort.unsorted();
            if ("priceLowHigh".equals(sortBy)) {
                sort = Sort.by(Sort.Direction.ASC, "basePrice");
            } else if ("priceHighLow".equals(sortBy)) {
                sort = Sort.by(Sort.Direction.DESC, "basePrice");
            } else if ("size".equals(sortBy)) {
                sort = Sort.by(Sort.Direction.DESC, "size");
            } else if ("availability".equals(sortBy)) {
                sort = Sort.by(Sort.Direction.DESC, "availability");
            }

            Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

            // Fetch paginated rooms
            Page<Room> roomPage = inventoryRepository.findAllBy(sortedPageable);

            // Optional filter by price
            if (maxPrice != null) {
                List<Room> filtered = roomPage.getContent().stream()
//                        .filter(r -> r.getBasePrice().toBigInteger().doubleValue() <= maxPrice)
                        .collect(Collectors.toList());

                return new PageImpl<>(
                        filtered.stream().map((element) -> modelMapper.map(element, RoomDto.class)).collect(Collectors.toList()),
                        sortedPageable,
                        filtered.size()
                );
            }

            // Return full paginated response
            return roomPage.map((element) -> modelMapper.map(element, RoomDto.class));
        }
    }



