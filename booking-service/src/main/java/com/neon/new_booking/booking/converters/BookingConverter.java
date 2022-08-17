package com.neon.new_booking.booking.converters;

import com.neon.new_booking.api.bookings.BookingDto;
import com.neon.new_booking.api.bookings.BookingItemDto;
import com.neon.new_booking.api.core.ApartmentDto;
import com.neon.new_booking.api.core.OrderCreateDtoRq;
import com.neon.new_booking.booking.models.Booking;
import com.neon.new_booking.booking.models.BookingItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingConverter {

    public OrderCreateDtoRq itemToOrderDto(String username, BookingItem bookingItem) {
        return new OrderCreateDtoRq(
                username,
                bookingItem.getApartmentId(),
                LocalDate.parse(bookingItem.getBookingStartDate()),
                LocalDate.parse(bookingItem.getBookingFinishDate()),
                bookingItem.getPricePerNight(),
                bookingItem.getPricePerOrder()
        );
    }

    public BookingDto modelToDto(Booking booking) {
        List<BookingItemDto> bookingItemDTOs = booking.getItems().stream().map(item ->
                new BookingItemDto(
                        item.getItemId(),
                        item.getApartmentId(),
                        item.getApartmentInfo(),
                        item.getApartmentAddress(),
                        item.getBookingStartDate(),
                        item.getBookingFinishDate(),
                        item.getBookingDuration(),
                        item.getPricePerNight(),
                        item.getPricePerOrder()
                )
        ).collect(Collectors.toList());
        return new BookingDto(bookingItemDTOs);
    }

    public BookingItemDto apartmentToBookingItemDto (ApartmentDto apartmentDto, String bookingStartDate, String bookingFinishDate){

        Integer bookingDuration = calculateDuration(bookingStartDate, bookingFinishDate);

        String info = apartmentDto.getCategory() + ", " +
                apartmentDto.getTitle() + ", " +
                apartmentDto.getNumberOfRooms() + " комн., на " +
                apartmentDto.getNumberOfBeds() + " мест.";

        String address = apartmentDto.getAddressDto().getCity()  + ", " +
                apartmentDto.getAddressDto().getStreet()  + ", " +
                apartmentDto.getAddressDto().getBuildingNumber()  + ", ";

        return new BookingItemDto(
                    null,
                    apartmentDto.getId(),
                    info,   // Apartment Info
                    address,// Apartment Address
                    bookingStartDate,
                    bookingFinishDate,
                    bookingDuration,
                    apartmentDto.getPricePerNight(),
                    // Стоимость заказа номера
                    recalculatePrice(apartmentDto.getPricePerNight(), bookingDuration)
                );
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
