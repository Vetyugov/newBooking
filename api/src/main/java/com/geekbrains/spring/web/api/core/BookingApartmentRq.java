package com.geekbrains.spring.web.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель для бронирования апартамента")
public class BookingApartmentRq {
    @Schema(description = "ID апартамента", required = true, example = "1")
    private Long id;

    @Schema(description = "Начальная дата бронирования", required = true, example = "4")
    private String bookingStartDate;

    @Schema(description = "Конечная дата бронирования", required = true, example = "4")
    private String bookingFinishDate;

    public Long getId() {
        return id;
    }

    public String getBookingStartDate() {
        return bookingStartDate;
    }

    public String getBookingFinishDate() {
        return bookingFinishDate;
    }

    @Override
    public String toString() {
        return "BookingApartmentDto{" +
                " bookingStartDate='" + bookingStartDate + '\'' +
                ", bookingFinishDate='" + bookingFinishDate + '\'' +
                '}';
    }

    public static class Builder {
        private final BookingApartmentRq bookingApartmentRq;

        public Builder() {
            this.bookingApartmentRq = new BookingApartmentRq();
        }

        public Builder id(Long id) {
            bookingApartmentRq.id = id;
            return this;
        }

        public Builder bookingStartDate(String bookingStartDate) {
            bookingApartmentRq.bookingStartDate = bookingStartDate;
            return this;
        }

        public Builder bookingFinishDate(String bookingFinishDate) {
            bookingApartmentRq.bookingFinishDate = bookingFinishDate;
            return this;
        }

        public BookingApartmentRq build() {
            return bookingApartmentRq;
        }
    }
}



