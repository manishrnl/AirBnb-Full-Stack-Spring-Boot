package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.BookingDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.ProfileUpdateRequestDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.UserDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.UserEntity;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.enums.Gender;
import org.example.tutorial_2_homework.manish_airbnb_clone.exception.ResourceNotFoundException;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static org.example.tutorial_2_homework.manish_airbnb_clone.util.AppUtils.getCurrentUser;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElse(null);
    }

    public UserDetails getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException("User with id " + userId + " not found"));
    }

    public UserDetails getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<BookingDto> getMyBookings() {
        return List.of(null);
    }

    public void updateProfile(ProfileUpdateRequestDto profileDto) {
        UserEntity userEntity = getCurrentUser();

        LocalDate dateOfBirth = profileDto.getDateOfBirth();
        String name = profileDto.getName();
        Gender gender = profileDto.getGender();

        if (name != null) userEntity.setName(name);
        if (gender != null) userEntity.setGender(gender);
        if (dateOfBirth != null) userEntity.setDateOfBirth(dateOfBirth);


        userRepository.save(userEntity);


    }

    public UserDto getMyProfiles() {
        UserEntity userEntity = getCurrentUser();
        Long userId = userEntity.getId();
        UserEntity getProfile =
                userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("We could not find users with id " + userId));

        return modelMapper.map(getProfile, UserDto.class);
    }
}
