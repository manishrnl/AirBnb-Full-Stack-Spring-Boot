package org.example.tutorial_2_homework.manish_airbnb_clone.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class RoomSearchRequestDto {
    private BigDecimal basePrice;
    private String city;

    private LocalDate startDate;
    private LocalDate endDate;
    private Integer roomsCount;

    private Integer pageNumber = 0;
    private Integer pageSize = 10;

//    private String sortBy;
//    private BigDecimal maxPrice;



}
