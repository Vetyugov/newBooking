package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.core.OrderCreateRq;
import com.geekbrains.spring.web.api.core.OrderUpdateRq;
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
    public Order createOrder(OrderCreateRq orderDto) {
        Order order = Order.builder()
                .username(orderDto.getUsername())
                .apartment(apartmentsService.findById(orderDto.getApartmentId()).get())
                .apartmentCheckIn(orderDto.getBookingStartDate())
                .apartmentCheckOut(orderDto.getBookingFinishDate())
                .price(orderDto.getPricePerNight())
                .totalPrice(orderDto.getPricePerOrder())
                .status(orderStatusService.findByDesc("awaiting payment").get())
                .build();
        return ordersRepository.save(order);
    }

    @Transactional
    public Order updateOrder(OrderUpdateRq orderDto) {
        Order order = Order.builder()
                .id(orderDto.getId())
                .username(orderDto.getUsername())
                .apartment(apartmentsService.findById(orderDto.getApartmentId()).get())
                .apartmentCheckIn(orderDto.getBookingStartDate())
                .apartmentCheckOut(orderDto.getBookingFinishDate())
                .price(orderDto.getPricePerNight())
                .totalPrice(orderDto.getPricePerOrder())
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

    public List<Order> findHostOrdersByUsername(String username) {
        return ordersRepository.findHostOrdersByUsername(username);
    }

    public List<Order> findInactiveOrdersByUsername(String username) {
        return ordersRepository.findInactiveByUsername(username);
    }

    public Optional<Order> findById(Long id) {
        return ordersRepository.findById(id);
    }

    public Order setStatusToOrder(Long id, String status){
        Order order = ordersRepository.getById(id);
        order.setStatus(orderStatusService.findByDesc(status).get());
         return ordersRepository.saveAndFlush(order);
    }
}
