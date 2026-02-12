package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import org.example.tutorial_2_homework.manish_airbnb_clone.dto.HotelDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.HotelInfoDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.HotelSearchRequest;

import java.util.List;

public interface HotelService {
    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel(Long hotelId);

    List<HotelDto> getAllHotels();


    HotelInfoDto getHotelInfoById(Long hotelId);
}
