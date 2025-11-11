package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.SignupDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.UserDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.UserEntity;
import org.example.tutorial_2_homework.manish_airbnb_clone.exception.ResourceNotFoundException;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User with email " + email + " not found"));
    }

    public UserDetails getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException("User with id " + userId + " not found"));

    }

    public UserDetails getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new BadCredentialsException("User with " +
                "email" +
                " " + email + " not found"));

    }

    public UserDto signup(SignupDto signupDto) {
        Optional<UserEntity> exists = userRepository.findByEmail(signupDto.getEmail());
        if (exists.isPresent()) {
            throw new ResourceNotFoundException("User with email " + signupDto.getEmail() +
                    " already exists");
        }

        UserEntity toBeCreated = modelMapper.map(signupDto, UserEntity.class);
        toBeCreated.setPassword(passwordEncoder.encode(toBeCreated.getPassword()));

        UserEntity saveUser = userRepository.save(toBeCreated);
        return modelMapper.map(saveUser, UserDto.class);

    }


}
