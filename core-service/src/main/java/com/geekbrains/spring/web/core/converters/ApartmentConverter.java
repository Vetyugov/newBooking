package com.geekbrains.spring.web.core.converters;

import com.geekbrains.spring.web.api.core.AddressDto;
import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.core.entities.Address;
import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.entities.ApartmentCategory;
import com.geekbrains.spring.web.core.services.ApartmentCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApartmentConverter {
    private final ApartmentCategoriesService apartmentCategoriesService;

    public Apartment apartmentDtoToEntity(ApartmentDto apartmentDto) {
        ApartmentCategory apartmentCategory = apartmentCategoriesService.getByTitle(apartmentDto.getCategory());
        Address address = new Address(apartmentDto.getAddressDto().getCity(),
                apartmentDto.getAddressDto().getStreet(),
                apartmentDto.getAddressDto().getBuildingNumber());
        return new Apartment.Builder()
                .title(apartmentDto.getTitle())
                .pricePerNight(apartmentDto.getPricePerNight())
                .apartmentCategory(apartmentCategory)
                .address(address)
                .squareMeters(apartmentDto.getSquareMeters())
                .numberOfGuests(apartmentDto.getNumberOfGuests())
                .numberOfRooms(apartmentDto.getNumberOfRooms())
                .numberOfBeds(apartmentDto.getNumberOfBeds())
                .username(apartmentDto.getUsername())
                .build();
    }

    public ApartmentDto entityToApartmentDto(Apartment apartment) {
        return new ApartmentDto.Builder()
                .id(apartment.getId())
                .title(apartment.getTitle())
                .pricePerNight(apartment.getPricePerNight())
                .category(apartment.getApartmentCategory().getTitle())
                .addressDto(apartment.getAddress().getCity(),
                        apartment.getAddress().getStreet(),
                        apartment.getAddress().getBuildingNumber())
                .squareMeters(apartment.getSquareMeters())
                .numberOfGuests(apartment.getNumberOfGuests())
                .numberOfRooms(apartment.getNumberOfRooms())
                .numberOfBeds(apartment.getNumberOfBeds())
                .username(apartment.getUsername())
                .build();
    }
}
