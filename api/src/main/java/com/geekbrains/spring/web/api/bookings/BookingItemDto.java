package com.geekbrains.spring.web.api.bookings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor

public class BookingItemDto {
    private Long apartmentId;
    private String apartmentTitle;
    private LocalDateTime apartmentCheckIn;
    private LocalDateTime apartmentCheckOut;
    private BigDecimal pricePerNight;
    private BigDecimal pricePerApartment;
    private Boolean status;

    public BookingItemDto(Long apartmentId, String apartmentTitle
//            ,LocalDateTime apartmentCheckIn,
//             LocalDateTime apartmentCheckOut
                          //TODO ЖЕНЯ
//            ,BigDecimal pricePerNight
            , BigDecimal pricePerApartment
//            ,Boolean status
    ) {
        this.apartmentId = apartmentId;
        this.apartmentTitle = apartmentTitle;
        this.apartmentCheckIn = apartmentCheckIn;
        this.apartmentCheckOut = apartmentCheckOut;
        this.pricePerNight = pricePerNight;
        this.pricePerApartment = pricePerApartment;
        this.status = status;
    }
}
