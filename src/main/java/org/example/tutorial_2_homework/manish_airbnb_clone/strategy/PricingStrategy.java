package org.example.tutorial_2_homework.manish_airbnb_clone.strategy;


import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {

    BigDecimal calculatePrice(Inventory inventory);
}
