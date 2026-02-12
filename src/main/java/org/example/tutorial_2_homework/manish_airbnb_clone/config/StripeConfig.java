package org.example.tutorial_2_homework.manish_airbnb_clone.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    public StripeConfig(@Value("${stripe.api.key}") String stripeKey) {
        Stripe.apiKey = stripeKey;


    }
}
