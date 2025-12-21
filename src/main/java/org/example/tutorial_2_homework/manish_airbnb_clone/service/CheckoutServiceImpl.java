package org.example.tutorial_2_homework.manish_airbnb_clone.service;

import com.stripe.model.Customer;
import com.stripe.model.checkout.Session;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.Booking;
import org.example.tutorial_2_homework.manish_airbnb_clone.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Override
    public String getCheckoutSession(Booking booking, String successUrl, String failureUrl) {
        log.info("Creating Sessions for booking id : {} ", booking.getId());
        UserEntity userEntity =
                (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            CustomerCreateParams customerCreateParams = CustomerCreateParams.builder()
                    .setName(userEntity.getName())
                    .setEmail(userEntity.getEmail())
                    .build();
            Customer customer = Customer.create(customerCreateParams);

            SessionCreateParams sessionParams = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                    .setCustomer(customer.getId())
                    .setSuccessUrl(successUrl)
                    .setCancelUrl(failureUrl)

                    .addLineItem(SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("inr")
                                    .setUnitAmount(booking.getAmount().multiply(BigDecimal.valueOf(100)).longValue())
                                    .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName(booking.getHotel().getName() + " : " + booking.getRoom().getType())
                                            .setDescription("Booking ID : " + booking.getId())
                                            .build()
                                    ).build()
                            ).build()
                    ).build();
            Session session = Session.create(sessionParams);
            log.info("Session created Successfully for booking with id : {} ", booking.getId());
            return session.getUrl();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
