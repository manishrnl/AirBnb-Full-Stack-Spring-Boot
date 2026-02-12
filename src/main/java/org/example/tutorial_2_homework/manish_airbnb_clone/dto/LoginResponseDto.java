package org.example.tutorial_2_homework.manish_airbnb_clone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponseDto {
    private Long id;
    private String accessToken;
    private String refreshToken;

    public LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public LoginResponseDto(Long id, String accessToken) {
        this.id = id;
        this.accessToken = accessToken;
    }

    public LoginResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
