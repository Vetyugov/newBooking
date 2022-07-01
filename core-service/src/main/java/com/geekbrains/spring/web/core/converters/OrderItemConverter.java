package com.geekbrains.spring.web.core.converters;

import com.geekbrains.spring.web.api.core.OrderItemDto;
import com.geekbrains.spring.web.core.entities.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {
    public OrderItem dtoToEntity(OrderItemDto orderItemDto) {
        throw new UnsupportedOperationException();
    }

    public OrderItemDto entityToDto(OrderItem orderItem) {
        return new OrderItemDto(orderItem.getApartment().getId(), orderItem.getApartment().getTitle(), orderItem.getQuantity(), orderItem.getPricePerApartment(), orderItem.getPrice());
    }
}
