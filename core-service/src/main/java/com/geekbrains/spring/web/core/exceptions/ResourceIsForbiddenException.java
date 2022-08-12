package com.geekbrains.spring.web.core.exceptions;

public class ResourceIsForbiddenException extends RuntimeException {
    public ResourceIsForbiddenException(String message) {
        super(message);
    }
}
