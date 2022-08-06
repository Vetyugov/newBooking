package com.geekbrains.spring.web.core.validators;

import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.core.exceptions.ValidationException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApartmentValidator {
    public void validate(ApartmentDto apartmentDto) {
        List<String> errors = new ArrayList<>();
        if (apartmentDto.getPricePerNight() == null & apartmentDto.getPricePerNight().compareTo(BigDecimal.ONE) < 0) {
            errors.add("Цена апартамента не может быть меньше 1");
        }
        if (apartmentDto.getTitle() == null & apartmentDto.getTitle().isBlank()) {
            errors.add("Апартамент не может иметь пустое название");
        }
        if (apartmentDto.getCategory() == null & apartmentDto.getCategory().isBlank()) {
            errors.add("Поле категория не может быть пустым");
        }
        if (apartmentDto.getTitle().isBlank()) {
            errors.add("Апартамент не может иметь пустое название");
        }
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
