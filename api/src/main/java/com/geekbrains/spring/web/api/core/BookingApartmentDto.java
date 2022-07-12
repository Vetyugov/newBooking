package com.geekbrains.spring.web.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель для бронирования апартамента")
public class BookingApartmentDto {
    @Schema(description = "ID апартамента", required = true, example = "1")
    private Long id;

    @Schema(description = "Название апартамента", required = true, maxLength = 255, minLength = 3, example = "")
    private String title;

    @Schema(description = "Цена апартамента за ночь", required = true, example = "1200.00")
    private BigDecimal pricePerNight;

    @Schema(description = "Категория апартамента", required = true, example = "Квартира")
    private String category;

    @Schema(description = "Название города", required = true, example = "Москва")
    private String city;

    @Schema(description = "Назавние улицы", required = true, example = "Ленина")
    private String street;

    @Schema(description = "Номер дома", required = true, example = "4")
    private Integer buildingNumber;

    @Schema(description = "Площадь помещения", required = true, example = "35")
    private Integer squareMeters;

    @Schema(description = "Максимально допустимое кол-во гостей", required = true, example = "3")
    private Integer numberOfGuests;

    @Schema(description = "Кол-во комнат", required = true, example = "2")
    private Integer numberOfRooms;

    @Schema(description = "Кол-во спальных мест", required = true, example = "4")
    private Integer numberOfBeds;

    @Schema(description = "Начальная дата бронирования", required = true, example = "4")
    private String bookingStartDate;

    @Schema(description = "Конечная дата бронирования", required = true, example = "4")
    private String bookingFinishDate;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public String getCategory() {
        return category;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public Integer getSquareMeters() {
        return squareMeters;
    }

    public Integer getNumberOfGuests() {
        return numberOfGuests;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public Integer getNumberOfBeds() {
        return numberOfBeds;
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
                "title='" + title + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", category='" + category + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNumber=" + buildingNumber +
                ", squareMeters=" + squareMeters +
                ", numberOfGuests=" + numberOfGuests +
                ", numberOfRooms=" + numberOfRooms +
                ", numberOfBeds=" + numberOfBeds +
                ", bookingStartDate='" + bookingStartDate + '\'' +
                ", bookingFinishDate='" + bookingFinishDate + '\'' +
                '}';
    }

    public static class Builder {
        private final BookingApartmentDto bookingApartmentDto;

        public Builder() {
            this.bookingApartmentDto = new BookingApartmentDto();
        }

        public Builder id(Long id) {
            bookingApartmentDto.id = id;
            return this;
        }

        public Builder title(String title) {
            bookingApartmentDto.title = title;
            return this;
        }

        public Builder pricePerNight(BigDecimal pricePerNight) {
            bookingApartmentDto.pricePerNight = pricePerNight;
            return this;
        }

        public Builder category(String category) {
            bookingApartmentDto.category = category;
            return this;
        }

        public Builder city(String city) {
            bookingApartmentDto.city = city;
            return this;
        }

        public Builder street(String street) {
            bookingApartmentDto.street = street;
            return this;
        }

        public Builder buildingNumber(Integer buildingNumber) {
            bookingApartmentDto.buildingNumber = buildingNumber;
            return this;
        }

        public Builder squareMeters(Integer squareMeters) {
            bookingApartmentDto.squareMeters = squareMeters;
            return this;
        }

        public Builder numberOfGuests(Integer numberOfGuests) {
            bookingApartmentDto.numberOfGuests = numberOfGuests;
            return this;
        }

        public Builder numberOfRooms(Integer numberOfRooms) {
            bookingApartmentDto.numberOfRooms = numberOfRooms;
            return this;
        }

        public Builder numberOfBeds(Integer numberOfBeds) {
            bookingApartmentDto.numberOfBeds = numberOfBeds;
            return this;
        }

        public Builder bookingStartDate(String bookingStartDate) {
            bookingApartmentDto.bookingStartDate = bookingStartDate;
            return this;
        }

        public Builder bookingFinishDate(String bookingFinishDate) {
            bookingApartmentDto.bookingFinishDate = bookingFinishDate;
            return this;
        }

        public BookingApartmentDto build() {
            return bookingApartmentDto;
        }
    }
}



