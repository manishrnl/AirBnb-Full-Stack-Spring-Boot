package org.example.tutorial_2_homework.manish_airbnb_clone.strategy;

import lombok.RequiredArgsConstructor;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PricingService {
    public BigDecimal calculateDyanamicPricing(Inventory inventory) {
        PricingStrategy pricingStrategy = new BasePricingStrategy();

        //Apply additional Strategy layers
        pricingStrategy = new SurgePricingStrategy(pricingStrategy);
        pricingStrategy = new OccupancyPricingStrategy(pricingStrategy);
        pricingStrategy = new UrgencyPricingStrategy(pricingStrategy);
        pricingStrategy = new HolidayPricingStrategy(pricingStrategy);


        return pricingStrategy.calculatePrice(inventory);
    }
}
