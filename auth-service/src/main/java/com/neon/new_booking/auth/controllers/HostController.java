package com.neon.new_booking.auth.controllers;

import com.neon.new_booking.api.dto.IndividualHostDto;
import com.neon.new_booking.api.dto.LegalHostDto;
import com.neon.new_booking.api.exceptions.AppError;
import com.neon.new_booking.auth.converters.HostConverter;
import com.neon.new_booking.auth.entities.Host;
import com.neon.new_booking.auth.services.HostService;
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
@RequestMapping("/com/neon/v1/hosts_accounts")
@RequiredArgsConstructor
@Slf4j
public class HostController {
    private final HostService hostService;
    private final HostConverter hostConverter;

    @GetMapping("/legal")
    @Operation(
            summary = "Получение информации о себе как о владельце по username (юрлицо)",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LegalHostDto.class))
                    )
            }
    )
    public LegalHostDto getLegalHostByUsername(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        log.info("ищем юрлицо по имени " + username);
        Host host = hostService.findByUsername(username);
        log.info("нашли физ лицо " + host);
        return hostConverter.entityToLegalHostDto(host);
    }

    @GetMapping("/individual")
    @Operation(
            summary = "Получение информации о себе как о владельце по username (юрлицо)",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = IndividualHostDto.class))
                    )
            }
    )
    public IndividualHostDto getIndividualHostByUsername(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        log.info("ищем физ лицо по имени " + username);
        Host host = hostService.findByUsername(username);
        log.info("нашли физ лицо " + host);
        return hostConverter.entityToIndividualHostDto(host);
    }


    @Operation(summary = "Обновление данных владельца (юрлица)")
    @ApiResponse(responseCode = "201",
            description = "Обновление данных прошло успешно")
    @ApiResponse(responseCode = "404", description = "Не корректные параметры запроса",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppError.class)))
    @PutMapping("/legal/")
    public ResponseEntity<?> updateLegalHost(@Valid @RequestBody LegalHostDto legalHostDto) {
        log.info("получен " + legalHostDto.getId());
        hostService.updateLegalHost(legalHostDto);
        log.info("перенаправлен в сервис " + legalHostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Обновление данных владельца (физлица)")
    @ApiResponse(responseCode = "201",
            description = "Обновление данных прошло успешно")
    @ApiResponse(responseCode = "404", description = "Не корректные параметры запроса",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AppError.class)))
    @PutMapping("/individual/")
    public ResponseEntity<?> updateIndividualHost(@Valid @RequestBody IndividualHostDto individualHostDto) {
        log.info("получен " + individualHostDto.getId());
        hostService.updateIndividualHost(individualHostDto);
        log.info("перенаправлен в сервис  " + individualHostDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}