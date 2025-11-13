package org.example.tutorial_2_homework.manish_airbnb_clone.strategy;

import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class SurgePricingStrategy implements PricingStrategy {

    private final  PricingStrategy pricingStrategy;


    @Override
    public BigDecimal calculatePrice(Inventory inventory) {

        return pricingStrategy
                .calculatePrice(inventory)
                .multiply(inventory.getSurgeFactor());
    }
}
