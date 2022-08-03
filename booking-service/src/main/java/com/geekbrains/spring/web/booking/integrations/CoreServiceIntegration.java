package com.geekbrains.spring.web.booking.models;

//import com.geekbrains.spring.web.api.core.ApartmentDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Optional;
//
//@Component
//@RequiredArgsConstructor
//public class ApartmentsServiceIntegration {
//    private final RestTemplate restTemplate;
//
//    @Value("${integrations.core-service.url}")
//    private String apartmentServiceUrl;
//
//    public Optional<ApartmentDto> findById(Long id) {
//        ApartmentDto apartmentDto = restTemplate.getForObject(apartmentServiceUrl + "/api/v1/apartments/" + id, ApartmentDto.class);
//        return Optional.ofNullable(apartmentDto);
//    }
//}

import com.geekbrains.spring.web.api.bookings.BookingDto;
import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.api.core.OrderCreateDtoRq;
import com.geekbrains.spring.web.api.exceptions.BookingServiceAppError;
import com.geekbrains.spring.web.booking.exceptions.CoreServiceIntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CoreServiceIntegration {
    private final WebClient coreServiceWebClient;

    public Optional<ApartmentDto> findById(Long id) {
        log.debug("CoreServiceWebClient findById " + id);
        ApartmentDto apartmentDto = coreServiceWebClient.get()
                .uri("/api/v1/apartments/" + id)
                // .bodyValue(body) // for POST
                .retrieve()
                .bodyToMono(ApartmentDto.class)
                .block();
        return Optional.ofNullable(apartmentDto);
    }

    public Optional<ResponseEntity> checkOrder(OrderCreateDtoRq orderDto) {
        log.debug("CoreServiceWebClient checkOrder");
        ResponseEntity orderChecked = coreServiceWebClient.post()
                .uri("/api/v1/orders")
                .bodyValue(orderDto) // for POST
                .retrieve()
                .bodyToMono(ResponseEntity.class)
                .block();
        return Optional.ofNullable(orderChecked);
    }

}
