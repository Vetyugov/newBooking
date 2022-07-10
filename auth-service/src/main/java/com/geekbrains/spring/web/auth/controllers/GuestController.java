package com.geekbrains.spring.web.auth.controllers;

import com.geekbrains.spring.web.api.dto.GuestDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/guest_account")
@RequiredArgsConstructor
@Slf4j
public class GuestController {
    private final GuestRepository guestRepository;
    private final GuestService guestService;
    private final GuestConverter guestConverter;

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение информации о себе как о госте по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = GuestDto.class))
                    )
            }
    )
    public GuestDto getGuestById(
            @PathVariable @Parameter(description = "ID гостя", required = true) Long id) {
        Guest guest = guestService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Guest not found, id: " + id));
        return guestConverter.entityToGuestDto(guest);
    }

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
        Guest guest = guestService.findByUsername(username);
        return guestConverter.entityToGuestDto(guest);
    }

//    public GuestDto getGuestInfo(Principal principal) {
//        return guestConverter.entityToGuestDto(guestService.findByUsername(principal.getName()));
//    }
}
