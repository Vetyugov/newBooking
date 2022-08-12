package com.geekbrains.spring.web.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Модель для бронирования апартамента")
public class BookingApartmentDtoRq {
    @Schema(description = "ID апартамента", required = true, example = "1")
    private Long id;

    @Schema(description = "Начальная дата бронирования", required = true, example = "2022-03-01")
    private LocalDate bookingStartDate;

    @Schema(description = "Конечная дата бронирования", required = true, example = "2022-03-05")
    private LocalDate bookingFinishDate;

    public Long getId() {
        return id;
    }

    public LocalDate getBookingStartDate() {
        return bookingStartDate;
    }

    public LocalDate getBookingFinishDate() {
        return bookingFinishDate;
    }


    @Override
    public String toString() {
        return "BookingApartmentDto{" +
                " id ='" + id + '\'' +
                " bookingStartDate='" + bookingStartDate + '\'' +
                ", bookingFinishDate='" + bookingFinishDate + '\'' +
                '}';
    }

    public static class Builder {
        private final BookingApartmentDtoRq bookingApartmentDtoRq;

        public Builder() {
            this.bookingApartmentDtoRq = new BookingApartmentDtoRq();
        }

        public Builder id(Long id) {
            bookingApartmentDtoRq.id = id;
            return this;
        }

        public Builder bookingStartDate(String bookingStartDate) {
            bookingApartmentDtoRq.bookingStartDate = LocalDate.parse(bookingStartDate);
            return this;
        }

        public Builder bookingFinishDate(String bookingFinishDate) {
            bookingApartmentDtoRq.bookingFinishDate = LocalDate.parse(bookingFinishDate);
            return this;
        }

        public Builder bookingStartDate(LocalDate bookingStartDate) {
            bookingApartmentDtoRq.bookingStartDate = bookingStartDate;
            return this;
        }

        public Builder bookingFinishDate(LocalDate bookingFinishDate) {
            bookingApartmentDtoRq.bookingFinishDate = bookingFinishDate;
            return this;
        }

        public BookingApartmentDtoRq build() {
            return bookingApartmentDtoRq;
        }
    }
}



