package org.example.tutorial_2_homework.manish_airbnb_clone.dto;

import org.example.tutorial_2_homework.manish_airbnb_clone.entity.HotelContactInfo;
import lombok.Data;

import java.util.List;

@Data
public class HotelDto {

    private Long id;
    private String name;
    private String city;

    private List<String> photos;
    private List<String> amenities;

    private HotelContactInfo contactInfo;
    private Boolean active;

}
