package com.geekbrains.spring.web.booking.models;

import com.geekbrains.spring.web.api.bookings.BookingItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingItem {
    private Long itemId;
    private Long apartmentId;
    private String apartmentInfo;
    private String apartmentAddress;
    private String bookingStartDate;
    private String bookingFinishDate;
    private Integer bookingDuration;
    private BigDecimal pricePerNight;
    private BigDecimal pricePerOrder;

    public BookingItem(BookingItemDto itemDto) {
        this.itemId = itemDto.getItemId();
        this.apartmentId = itemDto.getApartmentId();
        this.apartmentAddress = itemDto.getApartmentAddress();
        this.apartmentInfo = itemDto.getApartmentInfo();
        this.bookingStartDate = itemDto.getBookingStartDate();
        this.bookingFinishDate = itemDto.getBookingFinishDate();
        this.bookingDuration = itemDto.getBookingDuration();
        this.pricePerNight = itemDto.getPricePerNight();
        this.pricePerOrder = itemDto.getPricePerOrder();
    }
}
