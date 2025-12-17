package org.example.tutorial_2_homework.manish_airbnb_clone.strategy;

import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Inventory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public class HolidayPricingStrategy implements PricingStrategy {

    private final  PricingStrategy pricingStrategy;

    public HolidayPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = pricingStrategy.calculatePrice(inventory);

        boolean isHoliday = true; //call 3rd party apis to check holidays
        if (isHoliday)
            price = price.multiply(BigDecimal.valueOf(1.25)); // Increase by 25%
        return price;
    }
}
