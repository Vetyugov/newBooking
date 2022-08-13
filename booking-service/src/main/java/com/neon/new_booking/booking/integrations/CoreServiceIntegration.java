package com.neon.new_booking.booking.integrations;

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

import com.neon.new_booking.api.core.ApartmentDto;
import com.neon.new_booking.api.core.OrderCreateDtoRq;
import com.neon.new_booking.api.core.OrderDtoInfo;
import com.neon.new_booking.api.exceptions.BookingServiceAppError;
import com.neon.new_booking.booking.exceptions.BookingsBrokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CoreServiceIntegration {
    private final WebClient coreServiceWebClient;

    public Optional<ApartmentDto> findById(Long id) {
        log.info("CoreServiceWebClient findById " + id);
        ApartmentDto apartmentDto = coreServiceWebClient.get()
                .uri("/com/neon/new_booking/api/v1/apartments/" + id)
                // .bodyValue(body) // for POST
                .retrieve()
                .bodyToMono(ApartmentDto.class)
                .block();
        return Optional.ofNullable(apartmentDto);
    }

    public OrderDtoInfo checkOrder(OrderCreateDtoRq orderDto) throws BookingsBrokenException {
        log.info("CoreServiceWebClient checkOrder");
        OrderDtoInfo orderChecked = coreServiceWebClient.post()
                .uri("/com/neon/new_booking/api/v1/orders")
                .bodyValue(orderDto) // for POST
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError, // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(BookingServiceAppError.class).map(
                                body -> {
                                    if (body.getCode().equals(BookingServiceAppError.BookingServiceErrors.ORDER_IS_NOT_CREATED.name())) {
                                        return new BookingsBrokenException(body.getMessage());
                                    }
                                    return new BookingsBrokenException("Выполнен некорректный запрос к сервису корзин: причина неизвестна");
                                }
                        )
                )
                .bodyToMono(OrderDtoInfo.class)
                .block();
        return orderChecked;
    }

}
