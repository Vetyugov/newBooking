package com.geekbrains.spring.web.auth.controllers;

import com.geekbrains.spring.web.api.dto.IndividualHostDto;
import com.geekbrains.spring.web.api.dto.LegalHostDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.auth.converters.HostConverter;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/host_account")
@RequiredArgsConstructor
@Slf4j
public class HostController {
    private final HostRepository hostRepository;
    private final HostService hostService;
    private final HostConverter hostConverter;

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение информации о себе как о владельце (хозяине, юридическое лицо) по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = LegalHostDto.class))
                    )
            }
    )
    public LegalHostDto getLegalHostById(
            @PathVariable @Parameter(description = "ID владельца (юридического лица)", required = true) Long id) {
        Host host = hostService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Host not found, id: " + id));
        return hostConverter.entityToLegalHostDto(host);
    }

    @GetMapping("/indi/{id}")
    @Operation(
            summary = "Получение информации о себе как о владельце (хозяине, физическое лицо) по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = IndividualHostDto.class))
                    )
            }
    )
    public IndividualHostDto getIndividualHostById(
            @PathVariable @Parameter(description = "ID владельца (физического лица)", required = true) Long id) {
        Host host = hostService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Host not found, id: " + id));
        return hostConverter.entityToIndividualHostDto(host);
    }

//    @Operation(summary = "Получение информации о себе как о владельце (хозяине, юридическое лицо)")
//    @ApiResponse(responseCode = "200", description = "Информация о хозяине (юридическом лице)",
//            content = @Content(mediaType = "application/json",
//                    schema = @Schema(implementation = LegalHostDto.class)))
//    @GetMapping("/legalInfo")
//    public LegalHostDto getLegalHostInfo(Principal principal) {
//        return hostConverter.entityToLegalHostDto(hostService.findByUsername(principal.getName()));
//    }
//
//    @Operation(summary = "Получение информации о себе как о владельце (физическое лицо)")
//    @ApiResponse(responseCode = "200", description = "Информация о хозяине (физическое лицо)",
//            content = @Content(mediaType = "application/json",
//                    schema = @Schema(implementation = IndividualHostDto.class)))
//    @GetMapping("/individualInfo")
//    public IndividualHostDto getIndividualHostInfo(Principal principal) {
//        return hostConverter.entityToIndividualHostDto(hostService.findByUsername(principal.getName()));
//    }
}