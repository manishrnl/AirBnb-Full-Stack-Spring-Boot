package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Booking;

public interface CheckoutService {

    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);

}
