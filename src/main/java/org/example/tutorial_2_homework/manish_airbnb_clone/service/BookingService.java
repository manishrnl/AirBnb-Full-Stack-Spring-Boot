package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import com.stripe.model.Event;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.BookingDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.BookingRequest;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.GuestsDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.HotelReportDto;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    BookingDto initialiseBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestsDto> guestDtoList);

    String initiatePayments(Long bookingId);

    void capturePayment(Event event);

    void cancelBooking(Long bookingId);

    String getBookingStatus(Long bookingId);

    void confirmPaymentBySessionId(String sessionId);

    List<BookingDto> getAllBookingsByHotelId(Long hotelId);

    List<HotelReportDto> getHotelReport(Long hotelId, LocalDate startDate, LocalDate endDate);
}


