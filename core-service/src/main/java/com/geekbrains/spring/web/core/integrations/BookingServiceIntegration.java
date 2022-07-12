package com.geekbrains.spring.web.core.integrations;

import com.geekbrains.spring.web.api.bookings.BookingDto;
import com.geekbrains.spring.web.api.exceptions.BookingServiceAppError;
import com.geekbrains.spring.web.core.exceptions.BookingServiceIntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingServiceIntegration {
    private final WebClient bookingServiceWebClient;


    public void clearUserBooking(String username) {
        bookingServiceWebClient.get()
                .uri("/api/v1/booking/0/clear")
                .header("username", username)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public BookingDto getUserBooking(String username) {
        log.debug("bookingServiceWebClient");
        BookingDto booking = bookingServiceWebClient.get()
                .uri("/api/v1/booking/0")
                .header("username", username)
                // .bodyValue(body) // for POST
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.is4xxClientError(), // HttpStatus::is4xxClientError
                        clientResponse -> clientResponse.bodyToMono(BookingServiceAppError.class).map(
                                body -> {
                                    if (body.getCode().equals(BookingServiceAppError.BookingServiceErrors.BOOKING_NOT_FOUND.name())) {
                                        return new BookingServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: корзина не найдена");
                                    }
                                    if (body.getCode().equals(BookingServiceAppError.BookingServiceErrors.BOOKING_IS_BROKEN.name())) {
                                        return new BookingServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: корзина сломана");
                                    }
                                    return new BookingServiceIntegrationException("Выполнен некорректный запрос к сервису корзин: причина неизвестна");
                                }
                        )
                )
//                .onStatus(HttpStatus::is4xxClientError, clientResponse -> Mono.error(new BookingServiceIntegrationException("Выполнен некорректный запрос к сервису корзин")))
//                .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new BookingServiceIntegrationException("Сервис корзин сломался")))
                .bodyToMono(BookingDto.class)
                .block();
        return booking;
    }
}
