package com.geekbrains.spring.web.api.exceptions;

import com.geekbrains.spring.web.api.exceptions.AppError;

public class BookingServiceAppError extends AppError {
    public enum BookingServiceErrors {
        BOOKING_IS_BROKEN, BOOKING_ID_GENERATOR_DISABLED, BOOKING_NOT_FOUND, ORDER_IS_NOT_CREATED
    }

    public BookingServiceAppError(String code, String message) {
        super(code, message);
    }
}
