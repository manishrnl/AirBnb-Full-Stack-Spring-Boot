package org.example.tutorial_2_homework.manish_airbnb_clone.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateInventoryRequestDto {


    private LocalDate startDate;
    private LocalDate endDate;

    private BigDecimal surgeFactor;
    private Boolean closed;


}
