package org.example.tutorial_2_homework.manish_airbnb_clone.repository;

import org.example.tutorial_2_homework.manish_airbnb_clone.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

}
