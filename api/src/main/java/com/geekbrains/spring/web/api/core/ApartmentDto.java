package com.geekbrains.spring.web.api.core;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель для создания и отображения апартамента")
public class ApartmentDto {

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

    @Override
    public String toString() {
        return "ApartmentDto{" +
                ", title='" + title + '\'' +
                ", pricePerNight=" + pricePerNight +
                ", category='" + category + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", buildingNumber=" + buildingNumber +
                ", squareMeters=" + squareMeters +
                ", numberOfGuests=" + numberOfGuests +
                ", numberOfRooms=" + numberOfRooms +
                ", numberOfBeds=" + numberOfBeds +
                '}';
    }

    public static class Builder {
        private final ApartmentDto apartmentDto;

        public Builder() {
            this.apartmentDto = new ApartmentDto();
        }

        public Builder id(Long id) {
            apartmentDto.id = id;
            return this;
        }

        public Builder title(String title) {
            apartmentDto.title = title;
            return this;
        }

        public Builder pricePerNight(BigDecimal pricePerNight) {
            apartmentDto.pricePerNight = pricePerNight;
            return this;
        }

        public Builder category(String category) {
            apartmentDto.category = category;
            return this;
        }

        public Builder city(String city) {
            apartmentDto.city = city;
            return this;
        }

        public Builder street(String street) {
            apartmentDto.street = street;
            return this;
        }

        public Builder buildingNumber(Integer buildingNumber) {
            apartmentDto.buildingNumber = buildingNumber;
            return this;
        }

        public Builder squareMeters(Integer squareMeters) {
            apartmentDto.squareMeters = squareMeters;
            return this;
        }

        public Builder numberOfGuests(Integer numberOfGuests) {
            apartmentDto.numberOfGuests = numberOfGuests;
            return this;
        }

        public Builder numberOfRooms(Integer numberOfRooms) {
            apartmentDto.numberOfRooms = numberOfRooms;
            return this;
        }

        public Builder numberOfBeds(Integer numberOfBeds) {
            apartmentDto.numberOfBeds = numberOfBeds;
            return this;
        }

        public ApartmentDto build() {
            return apartmentDto;
        }
    }
}



