package com.neon.new_booking.core.exceptions;

public class ResourceIsForbiddenException extends RuntimeException {
    public ResourceIsForbiddenException(String message) {
        super(message);
    }
}
