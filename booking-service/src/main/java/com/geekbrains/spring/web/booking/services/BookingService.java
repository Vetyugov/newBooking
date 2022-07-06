package com.geekbrains.spring.web.booking.services;

import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;


import com.geekbrains.spring.web.booking.integrations.ApartmentsServiceIntegration;
import com.geekbrains.spring.web.booking.models.Booking;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final ApartmentsServiceIntegration apartmentsServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${utils.booking.prefix}")
    private String cartPrefix;

    public String getBookingUuidFromSuffix(String suffix) {
        return cartPrefix + suffix;
    }

    public String generateBookingUuid() {
        return UUID.randomUUID().toString();
    }

    public Booking getCurrentBooking(String cartKey) {
        if (!redisTemplate.hasKey(cartKey)) {
            redisTemplate.opsForValue().set(cartKey, new Booking());
        }
        return (Booking) redisTemplate.opsForValue().get(cartKey);
    }

    public void addToBooking(String cartKey, Long apartmentId) {
        log.info("Добавляем новый addToBooking");
        ApartmentDto apartmentDto = apartmentsServiceIntegration.findById(apartmentId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найдет, id: " + apartmentId));
        execute(cartKey, c -> {
            c.add(apartmentDto);
        });
    }

    public void clearBooking(String cartKey) {
        execute(cartKey, Booking::clear);
    }

    public void removeItemFromBooking(String cartKey, Long apartmentId) {
        execute(cartKey, c -> c.remove(apartmentId));
    }

    public void decrementItem(String cartKey, Long apartmentId) {
        execute(cartKey, c -> c.decrement(apartmentId));
    }

    public void merge(String userBookingKey, String incognitoBookingKey) {
        Booking incognitoBooking = getCurrentBooking(incognitoBookingKey);
        Booking userBooking = getCurrentBooking(userBookingKey);
        userBooking.merge(incognitoBooking);
        updateBooking(incognitoBookingKey, incognitoBooking);
        updateBooking(userBookingKey, userBooking);
    }

    private void execute(String cartKey, Consumer<Booking> action) {
        Booking cart = getCurrentBooking(cartKey);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartKey, cart);
    }

    public void updateBooking(String cartKey, Booking cart) {
        redisTemplate.opsForValue().set(cartKey, cart);
    }
}