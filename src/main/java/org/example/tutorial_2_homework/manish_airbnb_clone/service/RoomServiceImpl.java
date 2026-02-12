package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final ModelMapper modelMapper;

    @Override
    public RoomDto createNewRoom(Long hotelId, RoomDto roomDto) {
        log.info("Creating a new room in hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));

        UserEntity userEntity =
                (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userEntity.equals(hotel.getOwner()))
            throw new UnAuthorisedException("This user does not owns this room so can not "
                    + "create rooms in hotel with id : " + hotelId);


        Room room = modelMapper.map(roomDto, Room.class);
        room.setHotel(hotel);
        room.setCity(hotel.getCity());
        room = roomRepository.save(room);

        if (hotel.getActive()) {
            inventoryService.initializeRoomForAYear(room);
        }

        return modelMapper.map(room, RoomDto.class);
    }

    @Override
    public List<RoomDto> getAllRoomsInHotel(Long hotelId) {
        log.info("Getting all rooms in hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));

        UserEntity userEntity =
                (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userEntity.equals(hotel.getOwner()))
            throw new UnAuthorisedException("This user does not owns this room so can not " +
                    "view rooms with id : " + hotelId);


        return hotel.getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RoomDto getRoomById(Long roomId) {
        log.info("Getting the room with ID: {}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
        return modelMapper.map(room, RoomDto.class);
    }

    @Transactional
    @Override
    public void deleteRoomById(Long roomId) {
        log.info("Deleting the room with ID: {}", roomId);
        Room room = roomRepository
                .findById(roomId)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
        UserEntity userEntity =
                (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userEntity.equals(room.getHotel().getOwner()))
            throw new UnAuthorisedException("This user does not owns this room so can not " +
                    "delete rooms with id : " + roomId);

        inventoryService.deleteAllInventories(room);
        roomRepository.deleteById(roomId);
    }

    @Override
    @Transactional
    public RoomDto updateRoomById(Long hotelId, Long roomId, RoomDto roomDto) {
        log.info("Updating Room with ID: {} in Hotel with ID: {}", roomId, hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));

        UserEntity userEntity = getCurrentUser();
        if (!userEntity.equals(hotel.getOwner()))
            throw new RuntimeException("This user does not owns this room so can not update rooms with id : " + roomId);


        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId));
        modelMapper.map(roomDto, room);
        room.setId(roomId);


        // TODO update inventory and pricing as

        room = roomRepository.save(room);
        return modelMapper.map(room, RoomDto.class);
    }


}
