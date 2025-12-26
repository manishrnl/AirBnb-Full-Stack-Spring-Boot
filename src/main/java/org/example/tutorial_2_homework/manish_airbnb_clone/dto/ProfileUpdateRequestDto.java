package org.example.tutorial_2_homework.manish_airbnb_clone.dto;

import lombok.Data;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.enums.Gender;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequestDto {
    private String name;
    private Gender gender;
    private LocalDate dateOfBirth;


}
