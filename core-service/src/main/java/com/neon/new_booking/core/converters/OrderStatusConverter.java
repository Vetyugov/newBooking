package com.neon.new_booking.core.converters;

import com.neon.new_booking.api.core.OrderStatusDto;
import com.neon.new_booking.core.entities.Order;
import com.neon.new_booking.core.entities.OrderStatus;
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
