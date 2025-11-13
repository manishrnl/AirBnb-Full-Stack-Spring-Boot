package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import org.example.tutorial_2_homework.manish_airbnb_clone.dto.HotelPriceDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.HotelSearchRequest;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.RoomDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.RoomSearchRequestDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {

    void initializeRoomForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelPriceDto> searchHotels(HotelSearchRequest hotelSearchRequest);

//    Page sortRooms(Pageable pageable, RoomSearchRequestDto roomSearchRequestDto);

     Page<RoomDto> sortRooms(Pageable pageable, RoomSearchRequestDto req);
}
