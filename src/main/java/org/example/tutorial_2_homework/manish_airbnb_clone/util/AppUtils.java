package org.example.tutorial_2_homework.manish_airbnb_clone.util;

import org.example.tutorial_2_homework.manish_airbnb_clone.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class AppUtils {

    public static UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
