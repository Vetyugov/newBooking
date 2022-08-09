package com.geekbrains.spring.web.core.validators;

import com.geekbrains.spring.web.api.core.AddressDto;
import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.core.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApartmentValidator {
    private final List<String> errors = new ArrayList<>();

    public void validate(ApartmentDto apartmentDto) {
        validateAddress(apartmentDto.getAddressDto());
        if (apartmentDto.getPricePerNight() == null && apartmentDto.getPricePerNight().compareTo(BigDecimal.ONE) < 0) {
            errors.add("Цена апартамента не может быть меньше 1");
        }
        if (apartmentDto.getTitle() == null && apartmentDto.getTitle().isBlank()) {
            errors.add("Апартамент не может иметь пустое название");
        }
        if (apartmentDto.getCategory() == null && apartmentDto.getCategory().isBlank()) {
            errors.add("Поле категория не может быть пустым");
        }
        if(apartmentDto.getSquareMeters() == null && apartmentDto.getSquareMeters() <=0){
            errors.add("Поле площадь помещения не может быть пустым или меньше 1");
        }
        if(apartmentDto.getNumberOfGuests() == null && apartmentDto.getNumberOfGuests() <=0){
            errors.add("Поле кол-во гостей не может быть пустым или меньше 1");
        }
        if(apartmentDto.getNumberOfRooms() == null && apartmentDto.getNumberOfRooms() <=0){
            errors.add("Поле кол-во комнат не может быть пустым или меньше 1");
        }
        if(apartmentDto.getNumberOfBeds() == null && apartmentDto.getNumberOfBeds() <=0){
            errors.add("Поле кол-во спальных мест не может быть пустым или меньше 1");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validateAddress(AddressDto addressDto){
        if(addressDto == null){
            errors.add("Адрес апартамента не заполнен");
            return;
        }
        if(addressDto.getCity() == null && addressDto.getCity().isBlank()){
            errors.add("Поле город не может быть пустым");
        }
        if(addressDto.getStreet() == null && addressDto.getStreet().isBlank()){
            errors.add("Поле улица не может быть пустым");
        }
        if(addressDto.getBuildingNumber() == null && addressDto.getBuildingNumber() <=0){
            errors.add("Поле номер дома не может быть пустым или меньше 1");
        }
    }
}

