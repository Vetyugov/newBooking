package com.geekbrains.spring.web.booking.converters;

import com.geekbrains.spring.web.api.bookings.BookingDto;
import com.geekbrains.spring.web.api.bookings.BookingItemDto;
import com.geekbrains.spring.web.booking.models.Booking;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingConverter {
    public BookingDto modelToDto(Booking booking) {
        List<BookingItemDto> bookingItemDtos = booking.getItems().stream().map(it ->
                new BookingItemDto(it.getApartmentId(), it.getApartmentTitle()
//                        , it.getQuantity()
                        , it.getPricePerApartment()
//                        , it.getPrice()
                )
        ).collect(Collectors.toList());
        BookingDto bookingDto = new BookingDto(bookingItemDtos, booking.getTotalPrice());
        return bookingDto;
    }
}
