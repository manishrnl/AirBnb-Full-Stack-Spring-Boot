package org.example.tutorial_2_homework.manish_airbnb_clone.strategy;

import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Inventory;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UrgencyPricingStrategy implements PricingStrategy {

    private final PricingStrategy pricingStrategy;

    public UrgencyPricingStrategy(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {

        BigDecimal price = pricingStrategy.calculatePrice(inventory);
        LocalDate today = LocalDate.now();
        if (!inventory.getDate().isBefore(today) && inventory.getDate().isBefore(today.plusDays(7))) {
            price = price.multiply(BigDecimal.valueOf(1.15));
        }
        return price;
    }
}
