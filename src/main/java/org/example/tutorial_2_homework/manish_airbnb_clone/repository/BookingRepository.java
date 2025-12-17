package org.example.tutorial_2_homework.manish_airbnb_clone.repository;

import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
