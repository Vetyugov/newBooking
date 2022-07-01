package com.geekbrains.spring.web.booking.models;

import com.geekbrains.spring.web.api.core.ApartmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingItem {
    private Long apartmentId;
    private String apartmentTitle;
    private int quantity;
    private BigDecimal pricePerApartment;
    private BigDecimal price;

    public BookingItem(ApartmentDto apartmentDto) {
        this.apartmentId = apartmentDto.getId();
        this.apartmentTitle = apartmentDto.getTitle();
        this.quantity = 1;
        this.pricePerApartment = apartmentDto.getPrice();
        this.price = apartmentDto.getPrice();
    }

    public void changeQuantity(int delta) {
        this.quantity += delta;
        this.price = this.pricePerApartment.multiply(BigDecimal.valueOf(this.quantity));
    }
}
