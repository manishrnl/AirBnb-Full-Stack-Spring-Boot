package org.example.tutorial_2_homework.manish_airbnb_clone.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
