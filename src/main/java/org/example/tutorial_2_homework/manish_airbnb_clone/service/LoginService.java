package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.LoginDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.UserDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.UserEntity;
import org.example.tutorial_2_homework.manish_airbnb_clone.exception.ResourceNotFoundException;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public String login(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();


//        UserEntity user = userRepository
//                .findByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " does not exists. Please signup and then come back to login page"));
//
//        String passwordExtracted = user.getPassword();
//
//        if (!passwordEncoder.matches(password, passwordExtracted))
//            throw new ResourceNotFoundException("Password is incorrect. Please enter correct password");
//
//        else
//            return "";

        return jwtService.generateToken(userEntity);
    }


    public UserDto findNameByEmail(String email) {
        UserEntity users =
                userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Could not find Name with Email : " + email));
        System.out.println("Finding name By Email : " + users.getEmail());

        return modelMapper.map(users, UserDto.class);
    }
}
