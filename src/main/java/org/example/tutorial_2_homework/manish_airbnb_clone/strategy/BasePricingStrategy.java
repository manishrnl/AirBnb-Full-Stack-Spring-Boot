package org.example.tutorial_2_homework.manish_airbnb_clone.strategy;

import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Inventory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


public class BasePricingStrategy implements PricingStrategy {

    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getBasePrice();
    }
}
