package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import org.example.tutorial_2_homework.manish_airbnb_clone.dto.RoomDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Room;
import org.springframework.security.config.annotation.web.oauth2.resourceserver.OpaqueTokenDsl;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    RoomDto createNewRoom(Long hotelId, RoomDto roomDto);

    List<RoomDto> getAllRoomsInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId);

    void deleteRoomById(Long roomId);


    RoomDto updateRoomById(Long hotelId, Long roomId, RoomDto roomDto);

    List<RoomDto> getAllRooms();

    RoomDto getRoomsByRoomId(Long roomId);

    Optional<RoomDto> getHotelNameByRoomId(Long roomId);

    List<RoomDto> getAllRoomsByHotelId(Long hotelId);
}
