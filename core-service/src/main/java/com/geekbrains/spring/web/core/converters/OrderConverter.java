package com.geekbrains.spring.web.core.converters;

import com.geekbrains.spring.web.api.core.OrderDtoInfo;
import com.geekbrains.spring.web.core.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    public Order dtoToEntity(OrderDtoInfo orderDto) {
        throw new UnsupportedOperationException();
    }

    public OrderDtoInfo entityToDtoInfo(Order order) {
        OrderDtoInfo out = OrderDtoInfo.builder()
                .id(order.getId())
                .username(order.getUsername())
                .apartment_id(order.getApartment().getId())
                .apartmentCheckIn(order.getApartmentCheckIn())
                .apartmentCheckOut(order.getApartmentCheckOut())
                .price(order.getPrice())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus().getDescriptionRU())
                .build();
        return out;
    }
}
