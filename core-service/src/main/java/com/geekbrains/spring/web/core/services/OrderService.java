package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.core.BookingApartmentDtoRq;
import com.geekbrains.spring.web.api.core.OrderCreateDtoRq;
import com.geekbrains.spring.web.api.core.OrderUpdateRq;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.entities.Order;
import com.geekbrains.spring.web.core.entities.OrderStatus;
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

    private final BookingDatesService bookingDatesService;

    /**
     * Метод создание заказа
     *
     * @param orderCreateRq сущность запроса
     * @return заказ после сохранения в БД
     * @throws ResourceNotFoundException в случае, если не удалось создать даты бронирования
     */
    @Transactional
    public Order createOrder(OrderCreateDtoRq orderCreateRq) throws ResourceNotFoundException {
        Order order = Order.builder()
                .username(orderCreateRq.getUsername())
                .apartment(apartmentsService.findById(orderCreateRq.getApartmentId()).get())
                .bookingStartDate(orderCreateRq.getBookingStartDate())
                .bookingFinishDate(orderCreateRq.getBookingFinishDate())
                .price(orderCreateRq.getPricePerNight())
                .totalPrice(orderCreateRq.getPricePerOrder())
                .status(orderStatusService.findByDesc("awaiting payment").get())
                .build();
        //Создаем даты бронирования
        BookingApartmentDtoRq.Builder builder = new BookingApartmentDtoRq.Builder();
        BookingApartmentDtoRq bookingApartmentDto = builder
                .id(order.getApartment().getId())
                .bookingStartDate(order.getBookingStartDate())
                .bookingFinishDate(order.getBookingFinishDate())
                .build();
        bookingDatesService.createDateOfBooking(bookingApartmentDto);
        log.debug("Даты забронированы для " + bookingApartmentDto);
        return ordersRepository.save(order);
    }

    /**
     * Обновить заказ
     *
     * @param orderDto сущность для обновления заказа OrderUpdateRq
     * @return заказ, после обновления в БД
     */
    @Transactional
    public Order updateOrder(OrderUpdateRq orderDto) {
        Order order = Order.builder()
                .id(orderDto.getId())
                .username(orderDto.getUsername())
                .apartment(apartmentsService.findById(orderDto.getApartmentId()).get())
                .bookingStartDate(orderDto.getBookingStartDate())
                .bookingFinishDate(orderDto.getBookingFinishDate())
                .price(orderDto.getPricePerNight())
                .totalPrice(orderDto.getPricePerOrder())
                .status(orderStatusService.findById(orderDto.getStatus().getId()).get())
                .updatedAt(LocalDateTime.now())
                .build();
        return ordersRepository.saveAndFlush(order);
    }

    /**
     * Метод отмены заказа
     *
     * @param orderId id заказа
     * @return заказ
     * @throws ResourceNotFoundException в случае, если не удалось отменить даты бронирования, либо не удалось найти заказ по Id
     */
    public Order cancelOrder(Long orderId) throws ResourceNotFoundException {
        Order order = findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти заказ с id =" + orderId));
        BookingApartmentDtoRq.Builder builder = new BookingApartmentDtoRq.Builder();
        BookingApartmentDtoRq bookingApartmentDto = builder
                .id(order.getApartment().getId())
                .bookingStartDate(order.getBookingStartDate())
                .bookingFinishDate(order.getBookingFinishDate())
                .build();
        bookingDatesService.delete(bookingApartmentDto);
        log.debug("Сброшены даты бронирования для заказа " + order);
        OrderCreateDtoRq orderCreateDtoRq = OrderCreateDtoRq.builder()
                .username(order.getUsername())
                .bookingStartDate(order.getBookingStartDate())
                .bookingFinishDate(order.getBookingFinishDate())
                .pricePerOrder(order.getTotalPrice())
                .apartmentId(order.getApartment().getId())
                .pricePerNight(order.getPrice()).build();
        bookingServiceIntegration.recoveryBookingItem(orderCreateDtoRq);
        log.debug("Восстановлен в booking сервисе заказ " + orderCreateDtoRq);
        setStatusToOrder(order, "canceled");
        log.debug("Установлен статус canceled для заказа " + order);
        return order;
    }

    /**
     * Метод оплачивает заказ
     *
     * @param orderId id заказа
     * @return заказ
     * @throws ResourceNotFoundException в случае, если заказ не удалось найти по id
     */
    public Order payOrder(Long orderId) throws ResourceNotFoundException {
        Order order = findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти заказ с id " + orderId));
        return setStatusToOrder(order, "paid");
    }

    /**
     * Метод подтверждает заказ
     *
     * @param orderId id заказа
     * @return заказ
     * @throws ResourceNotFoundException в случае, если заказ не удалось найти по id
     */
    public Order confirmOrder(Long orderId) throws ResourceNotFoundException {
        Order order = findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти заказ с id " + orderId));
        return setStatusToOrder(order, "booked");
    }

    /**
     * Метод подтверждает проживание
     *
     * @param orderId id заказа
     * @return заказ
     * @throws ResourceNotFoundException в случае, если заказ не удалось найти
     */
    public Order confirmStay(Long orderId) throws ResourceNotFoundException {
        Order order = findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти заказ с id " + orderId));
        return setStatusToOrder(order, "completed");
    }

    /**
     * Поиск всех заказов арендатора по userName
     *
     * @param username никнейм арендатора
     * @return список заказов арендатора
     */
    public List<Order> findOrdersByUsername(String username) {
        return ordersRepository.findAllByUsername(username);
    }

    /**
     * Поиск всех активных заказов арендатора (статусы 'awaiting payment', 'paid', 'booked')
     *
     * @param username никнейм арендатора
     * @return список активных заказов арендатора
     */
    public List<Order> findActiveOrdersByUsername(String username) {
        return ordersRepository.findActiveByUsername(username);
    }

    /**
     * Поиск всех неактивных заказов арендатора (статусы 'canceled', 'completed')
     *
     * @param username никнейм арендатора
     * @return список активных заказов арендатора
     */
    public List<Order> findInactiveOrdersByUsername(String username) {
        return ordersRepository.findInactiveByUsername(username);
    }

    /**
     * Поиск всех заказов арендодателя
     *
     * @param username никнейм арендодателя
     * @return списко всех заказов арендатора
     */
    public List<Order> findHostOrdersByUsername(String username) {
        return ordersRepository.findHostOrdersByUsername(username);
    }

    /**
     * Найти заказ по id
     *
     * @param id идентификатор заказа
     * @return заказ, который возможно был найден
     */
    public Optional<Order> findById(Long id) {
        return ordersRepository.findById(id);
    }

    /**
     * Устанавливает в БД статус заказа
     *
     * @param order  заказ
     * @param status название из БД статуса заказа
     * @return заказ, обновленный в БД
     */
    private Order setStatusToOrder(Order order, String status) {
        Optional<OrderStatus> optionalStatus = orderStatusService.findByDesc(status);
        optionalStatus.ifPresent(order::setStatus);
        return ordersRepository.saveAndFlush(order);
    }
}
