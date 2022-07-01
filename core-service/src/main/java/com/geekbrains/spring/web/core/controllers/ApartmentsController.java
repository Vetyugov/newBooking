package com.geekbrains.spring.web.core.controllers;

import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.converters.ApartmentConverter;
import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.services.ApartmentsService;
import com.geekbrains.spring.web.core.validators.ApartmentValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/apartments")
@RequiredArgsConstructor
@Tag(name = "Продукты", description = "Методы работы с продуктами")
@Slf4j
public class ApartmentsController {
    private final ApartmentsService apartmentsService;
    private final ApartmentConverter apartmentConverter;
    private final ApartmentValidator apartmentValidator;

    @Operation(
            summary = "Запрос на получение страницы продуктов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    @GetMapping
    public Page<ApartmentDto> getAllApartments(
            @RequestParam(name = "p", defaultValue = "1") Integer page,
            @RequestParam(name = "min_price", required = false) Integer minPrice,
            @RequestParam(name = "max_price", required = false) Integer maxPrice,
            @RequestParam(name = "title_part", required = false) String titlePart
    ) {
        log.info("Запрос на получение списка апартаментов");
        if (page < 1) {
            page = 1;
        }
        return apartmentsService.findAll(minPrice, maxPrice, titlePart, page).map(
                p -> apartmentConverter.entityToDto(p)
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Запрос на получение апартамента по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ApartmentDto.class))
                    )
            }
    )
    public ApartmentDto getApartmentById(
            @PathVariable @Parameter(description = "Идентификатор апартаментов", required = true) Long id
    ) {
        Apartment apartment = apartmentsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Apartment not found, id: " + id));
        return apartmentConverter.entityToDto(apartment);
    }

    @PostMapping
    public ApartmentDto saveNewApartment(@RequestBody ApartmentDto apartmentDto) {
        apartmentValidator.validate(apartmentDto);
        Apartment apartment = apartmentConverter.dtoToEntity(apartmentDto);
        apartment = apartmentsService.save(apartment);
        return apartmentConverter.entityToDto(apartment);
    }

    @PutMapping
    public ApartmentDto updateApartment(@RequestBody ApartmentDto apartmentDto) {
        apartmentValidator.validate(apartmentDto);
        Apartment apartment = apartmentsService.update(apartmentDto);
        return apartmentConverter.entityToDto(apartment);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        apartmentsService.deleteById(id);
    }
}
