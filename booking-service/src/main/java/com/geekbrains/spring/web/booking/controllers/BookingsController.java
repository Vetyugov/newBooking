package com.geekbrains.spring.web.booking.controllers;

import com.geekbrains.spring.web.api.bookings.BookingDto;
import com.geekbrains.spring.web.api.dto.StringResponse;
import com.geekbrains.spring.web.booking.converters.BookingConverter;
import com.geekbrains.spring.web.booking.services.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingsController {
    private final BookingService bookingService;
    private final BookingConverter bookingConverter;

    @GetMapping("/{uuid}")
    public BookingDto getBooking(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        return bookingConverter.modelToDto(bookingService.getCurrentBooking(getCurrentBookingUuid(username, uuid)));
    }

    @GetMapping("/generate")
    public StringResponse getBooking() {
        return new StringResponse(bookingService.generateBookingUuid());
    }

    @GetMapping("/{uuid}/add/{productId}")
    public void add(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        log.info("Пришел запрос на добавление");
        bookingService.addToBooking(getCurrentBookingUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/decrement/{productId}")
    public void decrement(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        bookingService.decrementItem(getCurrentBookingUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/remove/{productId}")
    public void remove(@RequestHeader(required = false) String username, @PathVariable String uuid, @PathVariable Long productId) {
        bookingService.removeItemFromBooking(getCurrentBookingUuid(username, uuid), productId);
    }

    @GetMapping("/{uuid}/clear")
    public void clear(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        bookingService.clearBooking(getCurrentBookingUuid(username, uuid));
    }

    @GetMapping("/{uuid}/merge")
    public void merge(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        bookingService.merge(
                getCurrentBookingUuid(username, null),
                getCurrentBookingUuid(null, uuid)
        );
    }

    private String getCurrentBookingUuid(String username, String uuid) {
        if (username != null) {
            return bookingService.getBookingUuidFromSuffix(username);
        }
        return bookingService.getBookingUuidFromSuffix(uuid);
    }
}
