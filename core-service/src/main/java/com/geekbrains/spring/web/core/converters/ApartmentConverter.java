package com.geekbrains.spring.web.core.converters;

import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.entities.ApartmentCategory;
import com.geekbrains.spring.web.core.services.ApartmentCategoriesService;
import org.springframework.stereotype.Component;

@Component
public class ApartmentConverter {
    private ApartmentCategoriesService apartmentCategoriesService;

    public Apartment apartmentDtoToEntity(ApartmentDto apartmentDto) {
        ApartmentCategory apartmentCategory = apartmentCategoriesService.getByTitle(apartmentDto.getCategory());
        return new Apartment.Builder()
                .title(apartmentDto.getTitle())
                .pricePerNight(apartmentDto.getPricePerNight())
                .apartmentCategory(apartmentCategory)
                .city(apartmentDto.getCity())
                .street(apartmentDto.getStreet())
                .buildingNumber(apartmentDto.getBuildingNumber())
                .squareMeters(apartmentDto.getSquareMeters())
                .numberOfGuests(apartmentDto.getNumberOfGuests())
                .numberOfRooms(apartmentDto.getNumberOfRooms())
                .numberOfBeds(apartmentDto.getNumberOfBeds())
                .userName(apartmentDto.getUserName())
                .build();
    }

    public ApartmentDto entityToApartmentDto(Apartment apartment) {
        return new ApartmentDto.Builder()
                .id(apartment.getId())
                .title(apartment.getTitle())
                .pricePerNight(apartment.getPricePerNight())
                .category(apartment.getApartmentCategory().getTitle())
                .city(apartment.getCity())
                .street(apartment.getStreet())
                .buildingNumber(apartment.getBuildingNumber())
                .squareMeters(apartment.getSquareMeters())
                .numberOfGuests(apartment.getNumberOfGuests())
                .numberOfRooms(apartment.getNumberOfRooms())
                .numberOfBeds(apartment.getNumberOfBeds())
                .userName(apartment.getUserName())
                .build();
    }
}
