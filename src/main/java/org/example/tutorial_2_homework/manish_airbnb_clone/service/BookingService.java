package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import org.example.tutorial_2_homework.manish_airbnb_clone.dto.BookingDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.BookingRequest;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.GuestsDto;

import java.util.List;

public interface BookingService {


    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(long bookingId,List<GuestsDto> guestsDto);
}
