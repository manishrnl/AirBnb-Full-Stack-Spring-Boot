package org.example.tutorial_2_homework.manish_airbnb_clone.dto;

import lombok.Data;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.UserEntity;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.enums.Gender;

@Data
public class GuestsDto {

    private Long id;
    private UserEntity user;
    private String name;
    private Gender gender;
    private Integer age;
}
