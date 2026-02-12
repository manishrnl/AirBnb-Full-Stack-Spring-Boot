package org.example.tutorial_2_homework.manish_airbnb_clone.controller;

import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.InventoryDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.UpdateInventoryRequestDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/inventory")

public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<List<InventoryDto>> getAllInventoryByRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok(inventoryService.getAllInventoryByRoom(roomId));
    }

    @PatchMapping("/rooms/{roomId}")
    public ResponseEntity<Void> updateInventory(@PathVariable Long roomId,
                                                @RequestBody UpdateInventoryRequestDto updateInventoryDto) {
        return ResponseEntity.ok(inventoryService.updateInventory(roomId, updateInventoryDto));
    }

}
