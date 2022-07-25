package com.geekbrains.spring.web.booking.integrations;

import com.geekbrains.spring.web.api.core.OrderCreateDtoRq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrdersServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${integrations.core-service.url}")
    private String orderServiceUrl;

    public Optional<Boolean> checkOrder(OrderCreateDtoRq orderDto) {
        Boolean orderChecked = restTemplate.postForObject(orderServiceUrl + "/api/v1/orders", orderDto, Boolean.class);
        return Optional.ofNullable(orderChecked);
    }
}
