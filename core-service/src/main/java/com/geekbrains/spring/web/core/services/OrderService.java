package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.bookings.BookingDto;
import com.geekbrains.spring.web.api.core.OrderDtoCreate;
import com.geekbrains.spring.web.core.entities.Order;
import com.geekbrains.spring.web.core.integrations.BookingServiceIntegration;
import com.geekbrains.spring.web.core.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final BookingServiceIntegration bookingServiceIntegration;
    private final ApartmentsService apartmentsService;

    private final OrderStatusService orderStatusService;

    @Transactional
    public void createOrder(String username, OrderDtoCreate orderDto) {
        BookingDto currentBooking = bookingServiceIntegration.getUserBooking(username);
        log.debug("bookingServiceIntegration - ok");
        Order order = Order.builder()
                .username(orderDto.getUsername())
                .apartment(apartmentsService.findById(orderDto.getApartment().getId()).get())
                .apartmentCheckIn(orderDto.getApartmentCheckIn())
                .apartmentCheckOut(orderDto.getApartmentCheckOut())
                .price(orderDto.getPrice())
                .totalPrice(orderDto.getTotalPrice())
                .status(orderStatusService.findById(orderDto.getStatus().getId()).get())
                .build();
        ordersRepository.save(order);
        log.debug("ordersRepository.save - ok");
        bookingServiceIntegration.clearUserBooking(username);
        log.debug("clearUserBooking - ok");
    }

    @Transactional
    public Order updateOrder(OrderDtoCreate orderDto) {
        log.debug("bookingServiceIntegration - ok");
        Order order = Order.builder()
                .username(orderDto.getUsername())
                .apartment(apartmentsService.findById(orderDto.getApartment().getId()).get())
                .apartmentCheckIn(orderDto.getApartmentCheckIn())
                .apartmentCheckOut(orderDto.getApartmentCheckOut())
                .price(orderDto.getPrice())
                .totalPrice(orderDto.getTotalPrice())
                .status(orderStatusService.findById(orderDto.getStatus().getId()).get())
                .updatedAt(LocalDateTime.now())
                .build();
        return ordersRepository.saveAndFlush(order);
    }

    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }

    public List<Order> findActiveOrdersByUsername(String username) {
        return ordersRepository.findActiveByUsername(username);
    }

    public List<Order> findInactiveOrdersByUsername(String username) {
        return ordersRepository.findInactiveByUsername(username);
    }

    public Optional<Order> findById(Long id) {
        return ordersRepository.findById(id);
    }

    public void setStatusCanceledToOrder(Long id){
        Order order = ordersRepository.getById(id);
        order.setStatus(orderStatusService.findByDesc("canceled").get());
        ordersRepository.saveAndFlush(order);
    }
}
