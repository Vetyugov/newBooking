package com.neon.new_booking.core.exceptions;

public class OrderIsNotCreatedException extends RuntimeException{
    public OrderIsNotCreatedException(String message) {
        super(message);
    }
}
