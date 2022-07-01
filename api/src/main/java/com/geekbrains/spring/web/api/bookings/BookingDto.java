package com.geekbrains.spring.web.api.bookings;

import java.math.BigDecimal;
import java.util.List;

public class BookingDto {
    private List<BookingItemDto> items;
    private BigDecimal totalPrice;

    public List<BookingItemDto> getItems() {
        return items;
    }

    public void setItems(List<BookingItemDto> items) {
        this.items = items;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BookingDto() {
    }

    public BookingDto(List<BookingItemDto> items, BigDecimal totalPrice) {
        this.items = items;
        this.totalPrice = totalPrice;
    }
}
