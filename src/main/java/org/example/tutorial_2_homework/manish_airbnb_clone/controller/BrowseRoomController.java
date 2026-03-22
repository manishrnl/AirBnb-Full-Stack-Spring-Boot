package org.example.tutorial_2_homework.manish_airbnb_clone.controller;

import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.RoomDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.RoomRepository;
import org.example.tutorial_2_homework.manish_airbnb_clone.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class BrowseRoomController {

    private final RoomRepository roomRepository;
    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDto> getRoomsByRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomsByRoomId(roomId));
    }

    @GetMapping("/{roomId}/name")
    public ResponseEntity<Optional<RoomDto>> getHotelNameByRoomId(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getHotelNameByRoomId(roomId));
    }
}
