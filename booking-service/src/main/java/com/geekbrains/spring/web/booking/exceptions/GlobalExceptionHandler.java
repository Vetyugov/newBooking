package com.geekbrains.spring.web.booking.exceptions;

import com.geekbrains.spring.web.api.exceptions.BookingServiceAppError;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<BookingServiceAppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new BookingServiceAppError(BookingServiceAppError.BookingServiceErrors.BOOKING_IS_BROKEN.name(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<BookingServiceAppError> catchBookingIsBrokenException(BookingsBrokenException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new BookingServiceAppError(BookingServiceAppError.BookingServiceErrors.BOOKING_IS_BROKEN.name(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
