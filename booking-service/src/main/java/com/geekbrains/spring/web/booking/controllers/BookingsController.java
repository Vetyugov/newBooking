package com.geekbrains.spring.web.booking.controllers;

import com.geekbrains.spring.web.api.bookings.BookingDto;
import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.api.core.OrderCreateDtoRq;
import com.geekbrains.spring.web.api.dto.StringResponse;
import com.geekbrains.spring.web.booking.converters.BookingConverter;
import com.geekbrains.spring.web.booking.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/booking")
@Tag(name = "Бронирование", description = "Методы работы с выбранными пользователем апартаментами")
@RequiredArgsConstructor
@Slf4j
public class BookingsController {
    private final BookingService bookingService;
    private final BookingConverter bookingConverter;

    @Operation(
            summary = "Запрос на получение списка выбранных апартаментов пользователем",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookingDto.class))
                    )
            }
    )
    @GetMapping("/{uuid}")
    public BookingDto getBooking(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        return bookingConverter.modelToDto(bookingService.getCurrentBooking(getCurrentBookingUuid(username, uuid)));
    }

    @Operation(
            summary = "Запрос на получение индификатора списка для не авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            }
    )
    @GetMapping("/generate")
    public StringResponse getBooking() {
        return new StringResponse(bookingService.generateBookingUuid());
    }

    @GetMapping("/{uuid}/add")
    public void add(@RequestHeader(required = false) String username, @PathVariable String uuid,
                    @RequestParam(name = "id") @Parameter(description = "ID объекта") Long apartmentId,
                    @RequestParam(name = "start_date") @Parameter(description = "Дата начала бронирования") String bookingStartDate,
                    @RequestParam(name = "finish_date") @Parameter(description = "Дата конца бронирования") String bookingFinishDate
                   ) {
        log.info("Пришел запрос на добавление");
        bookingService.addToBooking(getCurrentBookingUuid(username, uuid), apartmentId, bookingStartDate, bookingFinishDate);
    }

    @PostMapping("/recovery")
    public void recovery(@RequestBody @Parameter(description = "Dto восстановления апартамента в списке бронирования", required = true) OrderCreateDtoRq orderDto) {
        log.info("Пришел запрос на восстановление");
        bookingService.addToBooking(
                getCurrentBookingUuid(orderDto.getUsername(), null),
                orderDto.getApartmentId(),
                orderDto.getBookingStartDate().toString(),
                orderDto.getBookingFinishDate().toString());
    }

    @GetMapping("/{uuid}/remove")
    public void remove(@RequestHeader(required = false) String username, @PathVariable String uuid,
                       @RequestParam(name = "id") @Parameter(description = "ID объекта") Long apartmentId,
                       @RequestParam(name = "start_date") @Parameter(description = "Дата начала бронирования") String bookingStartDate,
                       @RequestParam(name = "finish_date") @Parameter(description = "Дата конца бронирования") String bookingFinishDate
                    ) {
        bookingService.removeItemFromBooking(getCurrentBookingUuid(username, uuid), apartmentId, bookingStartDate, bookingFinishDate);
    }

    @GetMapping("/{uuid}/clear")
    public void clear(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        bookingService.clearBooking(getCurrentBookingUuid(username, uuid));
    }

    @GetMapping("/{uuid}/merge")
    public void merge(@RequestHeader(required = false) String username, @PathVariable String uuid) {
        bookingService.merge(
                getCurrentBookingUuid(username, null),
                getCurrentBookingUuid(null, uuid)
        );
    }

    @GetMapping("/{uuid}/choose")
    public BookingDto choose(@RequestHeader(required = false) String username, @PathVariable String uuid,
                       @RequestParam(name = "itemId") @Parameter(description = "ID в списке бронирования") Long itemId
                    ) {
        String currentBookingUuid = getCurrentBookingUuid(username, uuid);
        return bookingService.chooseItemFromBooking(currentBookingUuid, username, itemId);
    }

    private String getCurrentBookingUuid(String username, String uuid) {
        if (username != null) {
            return bookingService.getBookingUuidFromSuffix(username);
        }
        return bookingService.getBookingUuidFromSuffix(uuid);
    }
}
