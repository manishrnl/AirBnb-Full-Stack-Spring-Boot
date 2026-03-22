package org.example.tutorial_2_homework.manish_airbnb_clone.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RoomDto {
    private Long id;
    private String type;
    private BigDecimal basePrice;
    private List<String> photos;
    private List<String> amenities;
    private Integer totalCount;
    private Integer capacity;
    private String city;
    private String title; // Short catchy name for the room
    private String description; // Long detailed description
    private Integer bedrooms;
    private Integer beds;
    private Integer bathrooms;
    private Double rating;
    private Integer reviewCount;
    private String houseRules;

}
