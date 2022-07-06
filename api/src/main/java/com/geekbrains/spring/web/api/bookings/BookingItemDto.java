package com.geekbrains.spring.web.api.bookings;

import java.math.BigDecimal;

public class BookingItemDto {
    private Long apartmentId;
    private String apartmentTitle;
    private int quantity;
    private BigDecimal pricePerApartment;
    private BigDecimal price;

    public Long getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Long apartmentId) {
        this.apartmentId = apartmentId;
    }

    public String getApartmentTitle() {
        return apartmentTitle;
    }

    public void setApartmentTitle(String apartmentTitle) {
        this.apartmentTitle = apartmentTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPricePerApartment() {
        return pricePerApartment;
    }

    public void setPricePerApartment(BigDecimal pricePerApartment) {
        this.pricePerApartment = pricePerApartment;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BookingItemDto() {
    }

    public BookingItemDto(Long apartmentId, String apartmentTitle, int quantity, BigDecimal pricePerApartment, BigDecimal price) {
        this.apartmentId = apartmentId;
        this.apartmentTitle = apartmentTitle;
        this.quantity = quantity;
        this.pricePerApartment = pricePerApartment;
        this.price = price;
    }
}
