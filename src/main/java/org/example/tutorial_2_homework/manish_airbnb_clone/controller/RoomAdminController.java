package org.example.tutorial_2_homework.manish_airbnb_clone.controller;

import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.RoomDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.RoomSearchRequestDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.service.InventoryService;
import org.example.tutorial_2_homework.manish_airbnb_clone.service.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/hotels/{hotelId:\\d+}/rooms")

@RequiredArgsConstructor
public class RoomAdminController {

    private final RoomService roomService;
    private final InventoryService inventoryService;

    @PostMapping("/search")
    public ResponseEntity<Page<RoomDto>> sortRooms(
            Pageable pageable,
            @RequestBody RoomSearchRequestDto request) {

        System.out.println("➡️ Received RoomSearchRequestDto: " + request);

        Page<RoomDto> page = inventoryService.sortRooms(pageable, request);
        return ResponseEntity.ok(page);
    }


    @PostMapping
    public ResponseEntity<RoomDto> createNewRoom(@PathVariable Long hotelId,
                                                 @RequestBody RoomDto roomDto) {
        RoomDto room = roomService.createNewRoom(hotelId, roomDto);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<RoomDto>> getAllRoomsInHotel(@PathVariable Long hotelId) {
        return ResponseEntity.ok(roomService.getAllRoomsInHotel(hotelId));
    }

    @GetMapping("/{roomId:\\d+}")
    public ResponseEntity<RoomDto> getRoomById(@PathVariable Long hotelId, @PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.getRoomById(roomId));
    }

    @DeleteMapping("/{roomId:\\d+}")
    public ResponseEntity<RoomDto> deleteRoomById(@PathVariable Long roomId) {
        roomService.deleteRoomById(roomId);
        return ResponseEntity.noContent().build();
    }

}
