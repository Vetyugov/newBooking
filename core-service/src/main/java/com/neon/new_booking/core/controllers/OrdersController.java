package com.neon.new_booking.core.controllers;

import com.neon.new_booking.api.core.OrderCreateDtoRq;
import com.neon.new_booking.api.core.OrderDtoInfo;
import com.neon.new_booking.api.core.OrderOutCashRs;
import com.neon.new_booking.api.core.OrderStatusDto;
import com.neon.new_booking.api.exceptions.ResourceNotFoundException;
import com.neon.new_booking.core.converters.OrderConverter;
import com.neon.new_booking.core.converters.OrderStatusConverter;
import com.neon.new_booking.core.entities.Order;
import com.neon.new_booking.core.entities.OrderStatus;
import com.neon.new_booking.core.exceptions.BookingDatesIsNotDeletedException;
import com.neon.new_booking.core.services.ApartmentsService;
import com.neon.new_booking.core.services.OrderService;
import com.neon.new_booking.core.services.OrderStatusService;
import com.neon.new_booking.core.validators.OrderValidator;
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
@RequestMapping("/com/neon/new_booking/api/v1/orders")
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

    @Operation(
            summary = "Запрос на создание заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @PostMapping
    public OrderDtoInfo createOrder(@RequestBody @Parameter(description = "Структура заказа", required = true) OrderCreateDtoRq orderCreateRq) {
        log.info("Заявка на создание заказа " + orderCreateRq);
        Order order = orderService.createOrder(orderCreateRq);
        log.info("Создан новый заказ " + order);
        return orderConverter.entityToDtoInfo(order);
    }
    @Operation(
            summary = "Запрос на отмену заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/cancel/{orderId}")
    public OrderDtoInfo cancelOrder(@RequestHeader(required = false) @PathVariable @Parameter(description = "id заказа", required = true) Long orderId) throws BookingDatesIsNotDeletedException {
        log.info("Запрос на отмену заказа с id = " + orderId);
        Order order;
        try{
             order = orderService.cancelOrder(orderId);
        } catch (ResourceNotFoundException e){
            log.error("Не удалось удалить даты бронирования для заказа с id = " + orderId , e);
            throw new BookingDatesIsNotDeletedException("Не удалось удалить даты бронирования для заказа с id = " + orderId);
        }
        return orderConverter.entityToDtoInfo(order);
    }

    @Operation(
            summary = "Запрос на оплату заказа",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/pay/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderDtoInfo> payOrder(@RequestHeader(required = false) @PathVariable @Parameter(description = "id заказа", required = true) Long orderId) throws ResourceNotFoundException{
        log.info("Запрос на оплату заказа по id = " + orderId);
        Order order = orderService.payOrder(orderId);
        log.info("Оплачен заказ " + order);
        return new ResponseEntity(orderConverter.entityToDtoInfo(order), HttpStatus.OK);
    }

    @Operation(
            summary = "Запрос на подтверждение заказ хостом",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/confirmOrder/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderDtoInfo> confirmOrder(@RequestHeader(required = false) @PathVariable @Parameter(description = "id заказа", required = true) Long orderId) throws ResourceNotFoundException{
        log.info("Запрос на подтверждение заказа по id = " + orderId);
        Order order = orderService.confirmOrder(orderId);
        return new ResponseEntity<>(orderConverter.entityToDtoInfo(order), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Запрос на подтверждение проживания арендатором",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/confirmStay/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDtoInfo confirmStay(@RequestHeader(required = false) @PathVariable @Parameter(description = "id заказа", required = true) Long orderId) throws ResourceNotFoundException{
        log.info("Запрос на подтверждение проживания по id заказа = " + orderId);
        Order order = orderService.confirmStay(orderId);
        log.info("Проживание подтверждено " + order);
        return orderConverter.entityToDtoInfo(order);
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
        log.info("Запрос на вывод средств для заказа с id = " + id);
        if(orderService.cashOut(id)){
            return new OrderOutCashRs("Средства выведены");
        } else {
            return new OrderOutCashRs("Арендатор ещё не подтвердил факт проживания");
        }
    }




}
