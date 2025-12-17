package org.example.tutorial_2_homework.manish_airbnb_clone.dto;

import lombok.Data;
import org.springframework.http.HttpStatusCode;

@Data
public class UserDto  {
    private Long id;
    private String name;
    private String email;

}
