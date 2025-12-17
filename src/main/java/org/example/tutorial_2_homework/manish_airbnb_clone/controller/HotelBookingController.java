package org.example.tutorial_2_homework.manish_airbnb_clone.controller;

import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.BookingDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.BookingRequest;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.GuestsDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.BookingRepository;
import org.example.tutorial_2_homework.manish_airbnb_clone.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

    private final BookingRepository bookingRepository;
    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initialiseBooking(@RequestBody BookingRequest bookingRequest) {
        return ResponseEntity.ok(bookingService.initialiseBooking(bookingRequest));
    }


    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId ,
                                                @RequestBody List<GuestsDto> guestsDtoList) {
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestsDtoList));
    }


}
