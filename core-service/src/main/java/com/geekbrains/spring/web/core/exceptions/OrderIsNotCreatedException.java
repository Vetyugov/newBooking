package com.geekbrains.spring.web.core.exceptions;

public class OrderIsNotCreatedException extends RuntimeException{
    public OrderIsNotCreatedException(String message) {
        super(message);
    }
}
