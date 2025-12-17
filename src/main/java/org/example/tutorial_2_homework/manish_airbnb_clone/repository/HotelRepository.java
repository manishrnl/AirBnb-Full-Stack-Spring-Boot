package org.example.tutorial_2_homework.manish_airbnb_clone.repository;

import org.example.tutorial_2_homework.manish_airbnb_clone.dto.HotelDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
