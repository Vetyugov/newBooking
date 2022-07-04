package com.geekbrains.spring.web.core.controllers;

import com.geekbrains.spring.web.api.core.OrderDtoCreate;
import com.geekbrains.spring.web.api.core.OrderStatusDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.converters.OrderConverter;
import com.geekbrains.spring.web.api.core.OrderDtoInfo;
import com.geekbrains.spring.web.core.converters.OrderStatusConverter;
import com.geekbrains.spring.web.core.entities.Order;
import com.geekbrains.spring.web.core.entities.OrderStatus;
import com.geekbrains.spring.web.core.services.OrderService;
import com.geekbrains.spring.web.core.services.OrderStatusService;
import com.geekbrains.spring.web.core.validators.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    private final OrderStatusConverter orderStatusConverter;
    private final OrderValidator orderValidator;
    private final OrderStatusService orderStatusService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestHeader String username, @RequestBody OrderDtoCreate orderDetailsDto) {
        orderService.createOrder(username, orderDetailsDto);
    }

    @GetMapping
    public List<OrderDtoInfo> getCurrentUserOrders(@RequestHeader String username) {
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDtoInfo).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderDtoInfo getOrderById(@PathVariable Long id) {
        return orderConverter.entityToDtoInfo(orderService.findById(id).orElseThrow(() -> new ResourceNotFoundException("ORDER 404")));
    }

    @GetMapping("/statusList")
    @PutMapping
    public List<OrderStatusDto> getOrderStatusList() {
        List<OrderStatus> orderStatusList = orderStatusService.findAll();
        return orderStatusList.stream().map(s -> orderStatusConverter.entityToDto(s)).collect(Collectors.toList());
    }


}
