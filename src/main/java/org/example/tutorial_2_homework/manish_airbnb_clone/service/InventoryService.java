package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import org.example.tutorial_2_homework.manish_airbnb_clone.dto.*;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);

//    Page sortRooms(Pageable pageable, RoomSearchRequestDto roomSearchRequestDto);

    Page<RoomDto> sortRooms(Pageable pageable, RoomSearchRequestDto req);

    List<InventoryDto> getAllInventoryByRoom(Long roomId);

    Void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryDto);
}
