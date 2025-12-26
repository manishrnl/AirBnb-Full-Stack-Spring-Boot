package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.BookingDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.dto.ProfileUpdateRequestDto;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.enums.Gender;
import org.example.tutorial_2_homework.manish_airbnb_clone.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
        String name = profileDto.getName();
        LocalDate dateOfBirth = profileDto.getDateOfBirth();
        Gender gender = profileDto.getGender();

    }
}
