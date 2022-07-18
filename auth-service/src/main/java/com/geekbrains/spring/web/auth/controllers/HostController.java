package com.geekbrains.spring.web.auth.controllers;

import com.geekbrains.spring.web.api.core.ProfileDto;
import com.geekbrains.spring.web.api.dto.GuestDto;
import com.geekbrains.spring.web.api.dto.IndividualHostDto;
import com.geekbrains.spring.web.api.dto.LegalHostDto;
import com.geekbrains.spring.web.api.exceptions.AppError;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.auth.converters.HostConverter;
import com.geekbrains.spring.web.auth.entities.Guest;
import com.geekbrains.spring.web.auth.entities.Host;
import com.geekbrains.spring.web.auth.repositories.HostRepository;
import com.geekbrains.spring.web.auth.services.HostService;
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
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/hosts_account")
@RequiredArgsConstructor
@Slf4j
public class HostController {
    private final HostService hostService;
    private final HostConverter hostConverter;

    @GetMapping("/legal/{username}")
    @Operation(
            summary = "Получение информации о себе как о владельце по username (юрлицо)",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = GuestDto.class))
                    )
            }
    )
    public LegalHostDto getLegalHostByUsername(
            @PathVariable @Parameter(description = "Username владельца (юрлица)", required = true) String username) {
        Host host = hostService.findByUsername(username);
        return hostConverter.entityToLegalHostDto(host);
    }

    @GetMapping("/individual/{username}")
    @Operation(
            summary = "Получение информации о себе как о владельце по username (юрлицо)",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = GuestDto.class))
                    )
            }
    )
    public IndividualHostDto getIndividualHostByUsername(
            @PathVariable @Parameter(description = "Username владельца (юрлица)", required = true) String username) {
        Host host = hostService.findByUsername(username);
        return hostConverter.entityToIndividualHostDto(host);
    }
    @Operation(summary = "Обновление данных пользователя")
    @ApiResponse(responseCode = "201",
            description = "Обновление данных прошло успешно")
    @ApiResponse(responseCode = "404", description = "Не корректные параметры запроса",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppError.class)))
    @PostMapping
    public ResponseEntity<?> updateGuest(@Valid @RequestBody ProfileDto profileDto) {
//        if (!profileDto.getPassword().equals(profileDto.getPasswordConfirmation())) {
//            return new ResponseEntity<>(new AppError("BAD_REQUEST", "Пароли не совпадают в окне 'пароль' и 'подтвеждение"), HttpStatus.BAD_REQUEST);
//        }
//        if (userService.existByEmail(profileDto.getEmail())) {
//            return new ResponseEntity<>(new AppError("BAD_REQUEST", "Адрес электронной почты уже используется"), HttpStatus.BAD_REQUEST);
//        }
//        if (userService.existByUsername(profileDto.getUsername())) {
//            return new ResponseEntity<>(new AppError("BAD_REQUEST", "Логин уже существует"), HttpStatus.BAD_REQUEST);
//        }
//        userService.saveUser(profileDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}