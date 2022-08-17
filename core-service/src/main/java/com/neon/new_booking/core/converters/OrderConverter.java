package com.neon.new_booking.core.converters;

import com.neon.new_booking.api.core.OrderDtoInfo;
import com.neon.new_booking.core.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
                .apartmentId(order.getApartment().getId())
                .apartmentTitle(order.getApartment().getTitle())
                .apartmentCheckIn(order.getBookingStartDate())
                .apartmentCheckOut(order.getBookingFinishDate())
                .price(order.getPrice())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus().getDescriptionRU())
                .build();
        return out;
    }
}
