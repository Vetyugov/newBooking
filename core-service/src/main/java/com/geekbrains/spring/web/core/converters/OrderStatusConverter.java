package com.geekbrains.spring.web.core.converters;

import com.geekbrains.spring.web.api.core.OrderStatusDto;
import com.geekbrains.spring.web.core.entities.Order;
import com.geekbrains.spring.web.core.entities.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderStatusConverter {
    public Order dtoToEntity(OrderStatusDto orderStatusDto) {
        throw new UnsupportedOperationException();
    }

    public OrderStatusDto entityToDto(OrderStatus orderStatus) {
        OrderStatusDto out = OrderStatusDto.builder()
                .id(orderStatus.getId())
                .description(orderStatus.getDescriptionRU())
                .build();
        return out;
    }
}
