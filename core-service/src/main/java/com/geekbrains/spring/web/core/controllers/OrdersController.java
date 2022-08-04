package com.geekbrains.spring.web.core.controllers;

import com.geekbrains.spring.web.api.core.*;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.converters.OrderConverter;
import com.geekbrains.spring.web.core.converters.OrderStatusConverter;
import com.geekbrains.spring.web.core.entities.Order;
import com.geekbrains.spring.web.core.entities.OrderStatus;
import com.geekbrains.spring.web.core.exceptions.OrderIsNotCreatedException;
import com.geekbrains.spring.web.core.services.ApartmentsService;
import com.geekbrains.spring.web.core.services.BookingDatesService;
import com.geekbrains.spring.web.core.services.OrderService;
import com.geekbrains.spring.web.core.services.OrderStatusService;
import com.geekbrains.spring.web.core.validators.OrderValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Заказы", description = "Методы работы с заказами")
@RequiredArgsConstructor
@Slf4j
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    private final OrderStatusConverter orderStatusConverter;

    private final ApartmentsService apartmentsService;
    private final OrderValidator orderValidator;
    private final OrderStatusService orderStatusService;
    private final BookingDatesService bookingDatesService;


    @Operation(
            summary = "Запрос на создание заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<OrderDtoInfo> createOrder(@RequestBody @Parameter(description = "Структура заказа", required = true) OrderCreateDtoRq orderCreateRq) throws OrderIsNotCreatedException{
        //Проверяем свободны ли даты
        log.info("Создание заказа для " + orderCreateRq);
        BookingApartmentDtoRq.Builder builder = new BookingApartmentDtoRq.Builder();
        BookingApartmentDtoRq bookingApartmentDto =  builder
                .id(orderCreateRq.getApartmentId())
                .bookingStartDate(orderCreateRq.getBookingStartDate().toString())
                .bookingFinishDate(orderCreateRq.getBookingFinishDate().toString())
                .build();
        log.info("bookingApartmentDto = " + bookingApartmentDto);
        try {
            bookingDatesService.createDateOfBooking(bookingApartmentDto);
        }catch (ResourceNotFoundException e){
            throw new OrderIsNotCreatedException("This dates are invalid." + e.getMessage());
        }
        log.info("ура, создаем заказ");
        //Создаем заказ
        Order order = orderService.createOrder(orderCreateRq);
        if(order == null){
            throw new OrderIsNotCreatedException("Failed to create order " + orderCreateRq);
        }else {
            return new ResponseEntity<>(orderConverter.entityToDtoInfo(order), HttpStatus.CREATED);
        }

    }
    @GetMapping("/cancel/{orderId}")
    public ResponseEntity<OrderDtoInfo> cancelOrder(@RequestHeader(required = false) @PathVariable @Parameter(description = "id заказа", required = true) Long orderId) {
        log.info("Запрос на отмену заказа " + orderId);
        Order order = orderService.setStatusToOrder(orderId, "canceled");
        //TODO Попросить сервис апартаментов снять даты бронирования
        return new ResponseEntity(orderConverter.entityToDtoInfo(order), HttpStatus.OK);
    }

    @GetMapping("/pay/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderDtoInfo> payOrder(@RequestHeader(required = false) @PathVariable @Parameter(description = "id заказа", required = true) Long orderId) {
        Order order = orderService.setStatusToOrder(orderId, "paid");
        return new ResponseEntity(orderConverter.entityToDtoInfo(order), HttpStatus.OK);
    }

    @GetMapping("/confirmOrder/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderDtoInfo> confirmOrder(@RequestHeader(required = false) @PathVariable @Parameter(description = "id заказа", required = true) Long orderId) {
        Order order = orderService.setStatusToOrder(orderId, "booked");
        return new ResponseEntity(orderConverter.entityToDtoInfo(order), HttpStatus.OK);
    }

    @GetMapping("/confirmStay/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderDtoInfo> confirmStay(@RequestHeader(required = false) @PathVariable @Parameter(description = "id заказа", required = true) Long orderId) {
        Order order = orderService.setStatusToOrder(orderId, "completed");
        return new ResponseEntity(orderConverter.entityToDtoInfo(order), HttpStatus.OK);
    }

    @Operation(
            summary = "Запрос на получение списка активных заказов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ArrayList.class))
                    )
            }
    )
    @GetMapping
    public List<OrderDtoInfo> getCurrentUserActiveOrders(@RequestHeader @Parameter(description = "Никнейм пользователя", required = true) String username) {
        log.info("Запрос на получение заказа");
        return orderService.findActiveOrdersByUsername(username).stream()
                .map(orderConverter::entityToDtoInfo).collect(Collectors.toList());
    }

    @Operation(
            summary = "Запрос на получение списка заказов арендодателя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ArrayList.class))
                    )
            }
    )
    @GetMapping("/host")
    public List<OrderDtoInfo> getHostOrders(@RequestHeader @Parameter(description = "Никнейм пользователя", required = true) String username) {
        log.info("Запрос на получение заказа");
        return orderService.findHostOrdersByUsername(username).stream()
                .map(orderConverter::entityToDtoInfo).collect(Collectors.toList());
    }

    @Operation(
            summary = "Запрос на получение истоии заказов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ArrayList.class))
                    )
            }
    )
    @GetMapping("/history")
    public List<OrderDtoInfo> getCurrentUserOrdersHistory(@RequestHeader @Parameter(description = "Никнейм пользователя", required = true) String username) {
        log.info("Запрос на получение истории заказа");
        return orderService.findInactiveOrdersByUsername(username).stream()
                .map(orderConverter::entityToDtoInfo).collect(Collectors.toList());
    }

    @Operation(
            summary = "Запрос на получение заказа по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderDtoInfo.class))
                    )
            }
    )
    @GetMapping("/{id}")
    public OrderDtoInfo getOrderById(@PathVariable @Parameter(description = "Идентификатор заказа", required = true) Long id) {
        return orderConverter.entityToDtoInfo(orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ORDER 404")));
    }

    @Operation(
            summary = "Запрос на получение списка возможных статусов заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/statusList")
    @PutMapping
    public List<OrderStatusDto> getOrderStatusList() {
        List<OrderStatus> orderStatusList = orderStatusService.findAll();
        return orderStatusList.stream().map(s -> orderStatusConverter.entityToDto(s)).collect(Collectors.toList());
    }


    @Operation(
            summary = "Запрос на вывод средств с заказа по id заказа",
            responses = {
                    @ApiResponse(
                            description = "Сообщение с информацией о выводе", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = OrderDtoInfo.class))
                    )
            }
    )
    @GetMapping("/host/outCash/{id}")
    public OrderOutCashRs outCash(@PathVariable @Parameter(description = "Идентификатор заказа", required = true) Long id) {
        log.info("Запрос на вывод средств");
        Order order = orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ORDER 404"));
        if(order.getStatus().getDescription().equals("completed")){
            //ToDo выводим средства
            return new OrderOutCashRs("Средства выведены");
        } else {
            return new OrderOutCashRs("Арендатор ещё не подтвердил факт проживания");
        }
    }




}
