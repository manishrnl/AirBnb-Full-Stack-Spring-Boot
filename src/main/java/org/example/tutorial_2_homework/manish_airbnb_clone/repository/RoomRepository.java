package org.example.tutorial_2_homework.manish_airbnb_clone.repository;

import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Room findRoomById(Long roomId);

    // In RoomRepository.java
    @Query("SELECT r FROM Room r JOIN FETCH r.hotel WHERE r.id = :id")
    Optional<Room> findByIdWithHotel(@Param("id") Long id);

    List<Room> findAllByHotelId(Long hotelId);
// Note: Return type is Optional<Room>, NOT String
}
