package com.geekbrains.spring.web.booking.converters;

import com.geekbrains.spring.web.api.bookings.BookingDto;
import com.geekbrains.spring.web.api.bookings.BookingItemDto;
import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.booking.models.Booking;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingConverter {
    public BookingDto modelToDto(Booking booking) {
        List<BookingItemDto> bookingItemDTOs = booking.getItems().stream().map(item ->
                new BookingItemDto(
                        item.getApartmentId(),
                        item.getApartmentInfo(),
                        item.getApartmentAddress(),
                        item.getBookingStartDate(),
                        item.getBookingFinishDate(),
                        item.getBookingDuration(),
                        item.getPricePerNight(),
                        item.getPricePerOrder(),
                        item.getSelector()
                )
        ).collect(Collectors.toList());
        BookingDto bookingDto = new BookingDto(bookingItemDTOs);
        return bookingDto;
    }

    public BookingItemDto apartmentToBookingItem (ApartmentDto apartmentDto, String bookingStartDate, String bookingFinishDate){

        Integer bookingDuration = calculateDuration(bookingStartDate, bookingFinishDate);

        String info = apartmentDto.getCategory() + ", " +
                apartmentDto.getTitle() + ", " +
                apartmentDto.getNumberOfRooms() + " комн., на" +
                apartmentDto.getNumberOfBeds() + " мест.";

        String address = apartmentDto.getCity()  + ", " +
                apartmentDto.getStreet()  + ", " +
                apartmentDto.getBuildingNumber()  + ", ";

        BookingItemDto bookingItemDto = new BookingItemDto(
                    apartmentDto.getId(),
                    info,   // Apartment Info
                    address,// Apartment Address
                    bookingStartDate,
                    bookingFinishDate,
                    bookingDuration,
                    apartmentDto.getPricePerNight(),
                    // Стоимость заказа номера
                    recalculatePrice(apartmentDto.getPricePerNight(), bookingDuration),
                    true
                );
        return bookingItemDto;
    }

    public BigDecimal recalculatePrice(BigDecimal price, String bookingStartDate, String bookingFinishDate) {
        return price.multiply(BigDecimal.valueOf(calculateDuration(bookingStartDate, bookingFinishDate)));
    }

    public BigDecimal recalculatePrice(BigDecimal price, Integer duration) {
        return price.multiply(BigDecimal.valueOf(duration));
    }

    public Integer calculateDuration(String bookingStartDate, String bookingFinishDate) {
        LocalDate startDate = LocalDate.parse(bookingStartDate);
        LocalDate finishDate = LocalDate.parse(bookingFinishDate);

        Integer durationInDays = Period.between(startDate, finishDate).getDays();
        if(durationInDays < 1) durationInDays = 1;
        return durationInDays;
    }
}
