package org.example.tutorial_2_homework.manish_airbnb_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Hotel;


@Data
@NoArgsConstructor

public class HotelPriceDto {
    private Hotel hotel;
    private Double price;

    public HotelPriceDto(Hotel hotel, Double price) {
        this.hotel = hotel;
        this.price = price;
    }
}

