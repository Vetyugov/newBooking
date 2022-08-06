package com.geekbrains.spring.web.booking.services;

import com.geekbrains.spring.web.api.bookings.BookingDto;
import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;


import com.geekbrains.spring.web.booking.converters.BookingConverter;
import com.geekbrains.spring.web.booking.models.Booking;
import com.geekbrains.spring.web.booking.models.CoreServiceIntegration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {
    private final CoreServiceIntegration coreServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;
    private final BookingConverter bookingConverter;

    @Value("${utils.booking.prefix}")
    private String bookingPrefix;

    public String getBookingUuidFromSuffix(String suffix) {
        return bookingPrefix + suffix;
    }

    public String generateBookingUuid() {
        return UUID.randomUUID().toString();
    }

    public Booking getCurrentBooking(String bookingKey) {
        if (!redisTemplate.hasKey(bookingKey)) {
            redisTemplate.opsForValue().set(bookingKey, new Booking());
        }
        return (Booking) redisTemplate.opsForValue().get(bookingKey);
    }

    public void addToBooking(String bookingKey, Long apartmentId, String startDate, String finishDate) {
        log.info("Добавляем новый addToBooking");
        String start = startDate.substring(0, startDate.indexOf("T"));
        String finish = finishDate.substring(0, finishDate.indexOf("T"));

        log.info(startDate + " " + finishDate + " " + start  + " " + finish);

        ApartmentDto apartmentDto = coreServiceIntegration.findById(apartmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину. Продукт не найдет, id: " + apartmentId));
        execute(bookingKey, c -> c.add(bookingConverter.apartmentToBookingItemDto(apartmentDto, start, finish)));
    }

    public void clearBooking(String bookingKey) {
        execute(bookingKey, Booking::clear);
    }

    public void removeItemFromBooking(String bookingKey, Long apartmentId, String startData, String finishDate) {
        execute(bookingKey, c -> c.remove(apartmentId, startData, finishDate));
    }

    public BookingDto chooseItemFromBooking(String bookingKey, String username, Long itemId) {
        log.info("Пытаемся создать заказ");
        ResponseEntity checked = coreServiceIntegration.checkOrder(
                        bookingConverter.itemToOrderDto(username,getCurrentBooking(bookingKey).getItem(itemId))
                ).orElseThrow(() -> new ResourceNotFoundException("Невозможно оформить заказ. Апартаменты на выбранные даты бронирования заняты!"));
        log.info("вернулся ответ " + checked);
        if(checked.getStatusCode() == HttpStatus.CREATED) execute(bookingKey, c -> c.remove(itemId)); // Если заказ прошёл, то удаляем апартаменты из списка бронирования
        return bookingConverter.modelToDto(getCurrentBooking(bookingKey)); // Возвращаем обновлённый список бронирования
    }

    public void merge(String userBookingKey, String incognitoBookingKey) {
        Booking incognitoBooking = getCurrentBooking(incognitoBookingKey);
        Booking userBooking = getCurrentBooking(userBookingKey);
        userBooking.merge(incognitoBooking);
        updateBooking(incognitoBookingKey, incognitoBooking);
        updateBooking(userBookingKey, userBooking);
    }

    private void execute(String bookingKey, Consumer<Booking> action) {
        Booking booking = getCurrentBooking(bookingKey);
        action.accept(booking);
        redisTemplate.opsForValue().set(bookingKey, booking);
    }

    public void updateBooking(String bookingKey, Booking booking) {
        redisTemplate.opsForValue().set(bookingKey, booking);
    }
}