package org.example.tutorial_2_homework.manish_airbnb_clone.dto;

import lombok.Data;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestsDto> guests;
    private BigDecimal amount;

}

