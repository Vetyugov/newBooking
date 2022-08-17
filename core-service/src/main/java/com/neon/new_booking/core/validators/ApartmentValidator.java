package com.neon.new_booking.core.validators;

import com.neon.new_booking.api.core.AddressDto;
import com.neon.new_booking.api.core.ApartmentDto;
import com.neon.new_booking.core.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApartmentValidator {

    public void validate(ApartmentDto apartmentDto) {
        List<String> errors = validateAddress(apartmentDto.getAddressDto());

        if (apartmentDto.getPricePerNight() == null || apartmentDto.getPricePerNight().compareTo(BigDecimal.ONE) < 0) {
            errors.add("Цена апартамента не может быть меньше 1");
        }
        if (apartmentDto.getTitle() == null || apartmentDto.getTitle().isBlank()) {
            errors.add("Апартамент не может иметь пустое название");
        }
        if (apartmentDto.getCategory() == null || apartmentDto.getCategory().isBlank()) {
            errors.add("Поле категория не может быть пустым");
        }
        if (apartmentDto.getSquareMeters() == null || apartmentDto.getSquareMeters() <= 0) {
            errors.add("Поле площадь помещения не может быть пустым или меньше 1");
        }
        if (apartmentDto.getNumberOfGuests() == null || apartmentDto.getNumberOfGuests() <= 0) {
            errors.add("Поле кол-во гостей не может быть пустым или меньше 1");
        }
        if (apartmentDto.getNumberOfRooms() == null || apartmentDto.getNumberOfRooms() <= 0) {
            errors.add("Поле кол-во комнат не может быть пустым или меньше 1");
        }
        if (apartmentDto.getNumberOfBeds() == null || apartmentDto.getNumberOfBeds() <= 0) {
            errors.add("Поле кол-во спальных мест не может быть пустым или меньше 1");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private List<String> validateAddress(AddressDto addressDto) {
        List<String> errors = new ArrayList<>();
        if (addressDto == null) {
            errors.add("Адрес апартамента не заполнен");
            return errors;
        }
        if (addressDto.getCity() == null || addressDto.getCity().isBlank()) {
            errors.add("Поле город не может быть пустым");
        }
        if (addressDto.getStreet() == null || addressDto.getStreet().isBlank()) {
            errors.add("Поле улица не может быть пустым");
        }
        if (addressDto.getBuildingNumber() == null || addressDto.getBuildingNumber() <= 0) {
            errors.add("Поле номер дома не может быть пустым или меньше 1");
        }
        return errors;
    }
}

