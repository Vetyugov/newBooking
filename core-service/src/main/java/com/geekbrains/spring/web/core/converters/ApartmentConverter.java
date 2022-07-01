package com.geekbrains.spring.web.core.converters;

import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.core.entities.Apartment;
import org.springframework.stereotype.Component;

@Component
public class ApartmentConverter {
    public Apartment dtoToEntity(ApartmentDto apartmentDto) {
        return new Apartment(apartmentDto.getId(), apartmentDto.getTitle(), apartmentDto.getPrice());
    }

    public ApartmentDto entityToDto(Apartment apartment) {
        return new ApartmentDto(apartment.getId(), apartment.getTitle(), apartment.getPrice());
    }
}
