package com.geekbrains.spring.web.booking.models;

import com.geekbrains.spring.web.api.core.ApartmentDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingItem {
    private Long apartmentId;
    private String apartmentTitle;
    private LocalDateTime apartmentCheckIn;
    private LocalDateTime apartmentCheckOut;
    private BigDecimal pricePerNight;
    private BigDecimal pricePerApartment;
    private Boolean selector;

    public BookingItem(ApartmentDto apartmentDto) {
        this.apartmentId = apartmentDto.getId();
        this.apartmentTitle = apartmentDto.getTitle();
        this.pricePerNight = apartmentDto.getPricePerNight();
        this.selector = false;
        recalculatePrice();
    }

    public void recalculatePrice() {
        long durationInDays = Duration.between(this.apartmentCheckIn, this.apartmentCheckOut).toDays();
        if(durationInDays <= 1) durationInDays = 1;
        this.pricePerApartment = this.pricePerNight.multiply(BigDecimal.valueOf(durationInDays));
    }

}
