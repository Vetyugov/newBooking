package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.core.entities.Order;
import com.geekbrains.spring.web.core.integrations.BookingServiceIntegration;
import com.geekbrains.spring.web.core.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final BookingServiceIntegration bookingServiceIntegration;
    private final ApartmentsService apartmentsService;

//    @Transactional
//    public void createOrder(String username, OrderDto orderDto) {
//        BookingDto currentBooking = bookingServiceIntegration.getUserBooking(username);
//        log.debug("bookingServiceIntegration - ok");
//        Apartment apartment = apartmentsService.findById(orderDto.getApartment().getId()).get();
//        Order order = Order.builder()
//                .username(orderDto.getUsername())
//                .apartment(orderDto.getApartment())
//                .build()
//        order.setAddress(orderDetailsDto.getAddress());
//        order.setPhone(orderDetailsDto.getPhone());
//        order.setUsername(username);
//        order.setTotalPrice(currentBooking.getTotalPrice());
//        List<OrderItem> items = currentBooking.getItems().stream()
//                .map(o -> {
//                    OrderItem item = new OrderItem();
//                    item.setOrder(order);
//                    item.setQuantity(o.getQuantity());
//                    item.setPricePerApartment(o.getPricePerApartment());
//                    item.setPrice(o.getPrice());
//                    item.setApartment(apartmentsService.findById(o.getApartmentId()).orElseThrow(() -> new ResourceNotFoundException("Apartment not found")));
//                    return item;
//                }).collect(Collectors.toList());
//        order.setItems(items);
//        ordersRepository.save(order);
//        log.debug("ordersRepository.save - ok");
//        bookingServiceIntegration.clearUserBooking(username);
//        log.debug("clearUserBooking - ok");
//    }

    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }

    public Optional<Order> findById(Long id) {
        return ordersRepository.findById(id);
    }
}
