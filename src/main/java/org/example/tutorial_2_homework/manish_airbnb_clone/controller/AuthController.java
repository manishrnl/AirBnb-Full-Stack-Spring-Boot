package org.example.tutorial_2_homework.manish_airbnb_clone.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.LoginDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.SignupDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.UserDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.service.LoginService;
import org.example.tutorial_2_homework.manish_airbnb_clone.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;
    private final LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupDto signupDto) {
        UserDto userDto = userService.signup(signupDto);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto,
                                        HttpServletResponse response) {
        String token = loginService.login(loginDto);
        Cookie cookie = new Cookie("Jwt_Token", token);
        cookie.setHttpOnly(true); // Can not be traced by Hackers
        cookie.setSecure(true);  // works on https not on http

        response.addCookie(cookie);
//        return ResponseEntity.ok(token);

        return ResponseEntity.ok(token);

    }
}
