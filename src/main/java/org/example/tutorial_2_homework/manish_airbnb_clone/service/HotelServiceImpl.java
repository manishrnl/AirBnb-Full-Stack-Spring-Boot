package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.HotelDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.HotelInfoDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.RoomDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Hotel;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Room;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.UserEntity;
import org.example.tutorial_2_homework.manish_airbnb_clone.exception.ResourceNotFoundException;
import org.example.tutorial_2_homework.manish_airbnb_clone.exception.UnAuthorisedException;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.HotelRepository;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.tutorial_2_homework.manish_airbnb_clone.util.AppUtils.getCurrentUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;
    private final RoomRepository roomRepository;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        UserEntity userEntity = (UserEntity) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        hotel.setOwner(userEntity);
        hotel = hotelRepository.save(hotel);
        log.info("Successfully created hotel with ID: {}", hotel.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting the hotel with ID: {}", id);
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        UserEntity userEntity = getCurrentUser();
        if (!userEntity.equals(hotel.getOwner())) {
            throw new UnAuthorisedException("This user does not owns this hotel so can " +
                    "not view hotels with id : " + userEntity.getId());
        }
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating the hotel with ID: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        modelMapper.map(hotelDto, hotel);
        UserEntity userEntity = getCurrentUser();
        if (!userEntity.equals(hotel.getOwner())) {
            throw new UnAuthorisedException("This user does not owns this hotel so can " +
                    "not update hotels with id : " + userEntity.getId());
        }
        hotel.setId(id);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long id) {
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + id));
        UserEntity userEntity = getCurrentUser();
        if (!userEntity.equals(hotel.getOwner())) {
            throw new UnAuthorisedException("This user does not owns this hotel so can " +
                    "not delete hotels with id : " + userEntity.getId());
        }
        for (Room room : hotel.getRooms()) {
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
        UserEntity userEntity =
                getCurrentUser();
        if (!userEntity.equals(hotel.getOwner()))
            throw new UnAuthorisedException("This user does not owns this room so can not " +
                    "delete rooms with id : " + hotelId);

        hotel.setActive(true);

        // assuming only do it once
        for (Room room : hotel.getRooms()) {
            inventoryService.initializeRoomForAYear(room);
        }
    }

    @Override
    public List<HotelDto> getAllHotels() {
        UserEntity userEntity = getCurrentUser();
        log.info("Getting all hotels for the current user with id : {}", userEntity.getId());
        List<Hotel> hotels = hotelRepository.findByOwner(userEntity);
        log.info("Founded {} hotels for the current user with id : {}", hotels.size(), userEntity.getId());
        return hotels
                .stream()
                .map((element) -> modelMapper.map(element, HotelDto.class))
                .collect(Collectors.toList());


    }

    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel =
                hotelRepository.findById(hotelId).orElseThrow(() -> new ResourceNotFoundException("Could not find Hotels with Id : " + hotelId));
        List<RoomDto> rooms = hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();

        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }


}
