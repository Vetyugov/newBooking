package com.geekbrains.spring.web.auth.controllers;

import com.geekbrains.spring.web.api.dto.GuestDto;
import com.geekbrains.spring.web.api.exceptions.AppError;
import com.geekbrains.spring.web.auth.converters.GuestConverter;
import com.geekbrains.spring.web.auth.entities.Guest;
import com.geekbrains.spring.web.auth.repositories.GuestRepository;
import com.geekbrains.spring.web.auth.services.GuestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/guests_accounts")
@RequiredArgsConstructor
@Slf4j
public class GuestController {
    private final GuestRepository guestRepository;
    private final GuestService guestService;
    private final GuestConverter guestConverter;

//    @GetMapping("/{id}")
//    @Operation(
//            summary = "Получение информации о себе как о госте по id",
//            responses = {
//                    @ApiResponse(
//                            description = "Успешный ответ", responseCode = "200",
//                            content = @Content(schema = @Schema(implementation = GuestDto.class))
//                    )
//            }
//    )
//    public GuestDto getGuestById(
//            @PathVariable @Parameter(description = "ID гостя", required = true) Long id) {
//        Guest guest = guestService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Guest not found, id: " + id));
//        return guestConverter.entityToGuestDto(guest);
//    }

    @GetMapping("/{username}")
    @Operation(
            summary = "Получение информации о себе как о госте по username",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = GuestDto.class))
                    )
            }
    )
    public GuestDto getGuestByUsername(
            @PathVariable @Parameter(description = "Username гостя (пользователя)", required = true) String username) {
        log.info("ПОПАЛ");
        Guest guest = guestService.findByUsername(username);
        log.info("НАШЕЛ " + guest);
        return guestConverter.entityToGuestDto(guest);
    }

    @Operation(summary = "Обновление данных пользователя")
    @ApiResponse(responseCode = "201",
            description = "Обновление данных прошло успешно")
    @ApiResponse(responseCode = "404", description = "Не корректные параметры запроса",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppError.class)))
    @PostMapping
    public ResponseEntity<?> updateGuest(@Valid @RequestBody GuestDto guestDto) {
        log.info("получен " + guestDto.getId());
        guestService.updateGuest(guestDto);
        log.info("перенаправлен в сервис " + guestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
