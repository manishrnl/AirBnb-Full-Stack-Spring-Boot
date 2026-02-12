package org.example.tutorial_2_homework.manish_airbnb_clone.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.*;
import org.example.tutorial_2_homework.manish_airbnb_clone.security.AuthService;
import org.example.tutorial_2_homework.manish_airbnb_clone.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupRequestDto signupDto) {
        UserDto userDto = authService.signup(signupDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto,
                                                  HttpServletResponse response, RedirectAttributes redirectAttributes) {
        LoginResponseDto loginResponseDto = authService.login(loginDto);
        Cookie accessCookie = new Cookie("accessToken", loginResponseDto.getAccessToken());
        Cookie refreshCookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        accessCookie.setHttpOnly(true); // Can not be traced by Hackers
        accessCookie.setSecure(true);  // works on https not on http
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
        return ResponseEntity.ok(loginResponseDto);

    }

    @PostMapping("/findNameByEmail")
    public ResponseEntity<UserDto> findByEmail(@RequestBody EmailDto email) {
        UserDto users = authService.findNameByEmail(email.getEmail());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshToken(HttpServletRequest request) {

        String refreshToken = Arrays.stream(request.getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(cookie -> cookie.getValue())
                .orElseThrow(() -> new AuthenticationServiceException("Refresh Token not " +
                        "found inside cookies"));
        String accessToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }
}
