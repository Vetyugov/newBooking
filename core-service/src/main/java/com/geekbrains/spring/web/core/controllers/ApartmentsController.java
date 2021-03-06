package com.geekbrains.spring.web.core.controllers;

import com.geekbrains.spring.web.api.core.BookingApartmentDto;
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
@Tag(name = "Апартаменты", description = "Методы работы с апартаментами")
@Slf4j
public class ApartmentsController {
    private final ApartmentsService apartmentsService;
    private final ApartmentConverter apartmentConverter;
    private final ApartmentValidator apartmentValidator;

    @Operation(
            summary = "Запрос на получение страницы апартаментов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    @GetMapping
    public Page<ApartmentDto> getAllApartments(
            @RequestParam(name = "p", defaultValue = "1") @Parameter(description = "Номер страницы") Integer page,
            @RequestParam(name = "min_price", required = false) @Parameter(description = "Минимальная цена") Integer minPrice,
            @RequestParam(name = "max_price", required = false) @Parameter(description = "Максимальная цена") Integer maxPrice,
            @RequestParam(name = "title_part", required = false) @Parameter(description = "Часть названия апартамента") String titlePart,
            @RequestParam(name = "category_part", required = false) @Parameter(description = "Часть названия категории") String categoryPart
    ) {
        log.info("Запрос на получение списка апартаментов");
        if (page < 1) {
            page = 1;
        }
        return apartmentsService.findAll(minPrice, maxPrice, titlePart, categoryPart, page).map(
                apartmentConverter::entityToApartmentDto
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
    public ApartmentDto getApartmentById(@PathVariable @Parameter(description = "Иденификатор апартамента", required = true) Long id) {
        Apartment apartment = apartmentsService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Apartment not found, id: " + id));
        return apartmentConverter.entityToApartmentDto(apartment);
    }

    @Operation(
            summary = "Запрос на добавление нового апартамента",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ApartmentDto.class))
                    )
            }

    )
    @PostMapping
    public ApartmentDto saveNewApartment(@RequestBody @Parameter(description = "Dto для создания апартамента", required = true) ApartmentDto apartmentDto) {
        apartmentValidator.validate(apartmentDto);
        Apartment apartment = apartmentConverter.apartmentDtoToEntity(apartmentDto);
        apartment = apartmentsService.save(apartment);
        return apartmentConverter.entityToApartmentDto(apartment);
    }

   /* @Operation(
            summary = "Запрос на изменение существующего апартамента",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ApartmentDto.class))
                    )
            }

    )
    @PutMapping
    public ApartmentDto updateApartment(@RequestBody @Parameter(description = "Dto апартамента", required = true) ApartmentDto apartmentDto) {
        apartmentValidator.validate(apartmentDto);
        Apartment apartment = apartmentsService.update(apartmentDto);
        return apartmentConverter.entityToApartmentDto(apartment);
    }*/

    @Operation(
            summary = "Запрос на добавление дат бронирования апартамента",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Integer.class))
                    )
            }

    )
    @PutMapping
    public Integer createDateOfBooking(@RequestBody @Parameter(description = "Dto для изменения дат бронирования апартамента", required = true) BookingApartmentDto bookingApartmentDto) {
       // apartmentValidator.validate(apartmentDto);
        Integer numberOfBookedDays = apartmentsService.createDateOfBooking(bookingApartmentDto);
        return numberOfBookedDays;
    }

    @Operation(
            summary = "Запрос на удаление апартамента по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }

    )
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable @Parameter(description = "ID апартамента", required = true) Long id) {
        apartmentsService.deleteById(id);
    }
}
