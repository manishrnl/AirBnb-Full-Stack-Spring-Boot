package org.example.tutorial_2_homework.manish_airbnb_clone.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.enums.BookingStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingStatusResponseDto {
    private BookingStatus bookingStatus;
}
