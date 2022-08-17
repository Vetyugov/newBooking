package com.neon.new_booking.core.validators;

import com.neon.new_booking.api.core.OrderCreateDtoRq;
import com.neon.new_booking.core.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderValidator {
        public void validate(OrderCreateDtoRq orderDto) {
            List<String> errors = new ArrayList<>();
            if (orderDto.getPricePerNight().compareTo(BigDecimal.ONE) < 0) {
                errors.add("Цена апартамента не может быть меньше 1");
            }
            if (orderDto.getPricePerOrder().compareTo(BigDecimal.ONE) < 0) {
                errors.add("Суммарная цена проживания не может быть меньше 1");
            }
            if (!errors.isEmpty()) {
                throw new ValidationException(errors);
            }
        }
}
