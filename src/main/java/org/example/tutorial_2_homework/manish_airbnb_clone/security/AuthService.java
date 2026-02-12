package org.example.tutorial_2_homework.manish_airbnb_clone.security;

import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.LoginDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.LoginResponseDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.SignupRequestDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.UserDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.UserEntity;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.enums.Role;
import org.example.tutorial_2_homework.manish_airbnb_clone.exception.ResourceNotFoundException;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.UserRepository;
import org.example.tutorial_2_homework.manish_airbnb_clone.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserDto signup(SignupRequestDto signupDto) {
        UserEntity exists =
                userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if (exists != null) {
            throw new RuntimeException("User with email " + signupDto.getEmail() +
                    " already exists");
        }

        UserEntity newUser = modelMapper.map(signupDto, UserEntity.class);
        newUser.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        newUser.setRoles(Set.of(Role.HOTEL_MANAGER));
        newUser = userRepository.save(newUser);
        return modelMapper.map(newUser, UserDto.class);

    }

    public LoginResponseDto login(LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        UserEntity userEntity = (UserEntity) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);
        Long userId = userEntity.getId();
        return new LoginResponseDto(userId, accessToken, refreshToken);
    }


    public UserDto findNameByEmail(String email) {
        UserEntity users =
                userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Could not find Name with Email : " + email));
        System.out.println("Finding name By Email : " + users.getEmail());

        return modelMapper.map(users, UserDto.class);
    }

    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        UserEntity userEntity =
                userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found with id : " + userId));

        return jwtService.generateAccessToken(userEntity);

    }
}
