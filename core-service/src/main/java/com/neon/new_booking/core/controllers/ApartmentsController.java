package com.neon.new_booking.core.controllers;

import com.neon.new_booking.api.core.ApartmentDto;
import com.neon.new_booking.api.exceptions.AppError;
import com.neon.new_booking.api.exceptions.ResourceNotFoundException;
import com.neon.new_booking.core.entities.Apartment;
import com.neon.new_booking.core.validators.ApartmentValidator;
import com.neon.new_booking.core.converters.ApartmentConverter;
import com.neon.new_booking.core.services.ApartmentsService;
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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/com/neon/new_booking/api/v1/apartments")
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
            @RequestParam(name = "city_part", required = false) @Parameter(description = "Часть названия города") String cityPart,
            @RequestParam(name = "min_price", required = false) @Parameter(description = "Минимальная цена") Integer minPrice,
            @RequestParam(name = "max_price", required = false) @Parameter(description = "Максимальная цена") Integer maxPrice,
            @RequestParam(name = "min_square_meters", required = false) @Parameter(description = "Минимальная площадь") Integer minSquareMeters,
            @RequestParam(name = "max_square_meters", required = false) @Parameter(description = "Максимальная площадь") Integer maxSquareMeters,
            @RequestParam(name = "number_of_guests", required = false) @Parameter(description = "Количество гостей") Integer numberOfGuests,
            @RequestParam(name = "number_of_rooms", required = false) @Parameter(description = "Количество комнат") Integer numberOfRooms,
            @RequestParam(name = "number_of_beds", required = false) @Parameter(description = "Количество спальных мест") Integer numberOfBeds,
            @RequestParam(name = "title_part", required = false) @Parameter(description = "Часть названия апартамента") String titlePart,
            @RequestParam(name = "category_part", required = false) @Parameter(description = "Часть названия категории") String categoryPart,
            @RequestParam(name = "start_date", required = false) @Parameter(description = "Дата начала бронирования") String startDate,
            @RequestParam(name = "finish_date", required = false) @Parameter(description = "Дата конца бронирования") String finishDate
    ) {
        log.info("Запрос на получение списка апартаментов");
        if (page < 1) {
            page = 1;
        }
        return apartmentsService.findAll(cityPart, minPrice, maxPrice, minSquareMeters, maxSquareMeters, numberOfGuests, numberOfRooms, numberOfBeds, titlePart, categoryPart, startDate, finishDate, page).map(
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

    @GetMapping("/inactive/{id}")
    @Operation(
            summary = "Запрос на получение неактивного апартамента по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ApartmentDto.class))
                    )
            }
    )
    public ApartmentDto getInactiveApartmentById(@PathVariable @Parameter(description = "Иденификатор апартамента", required = true) Long id) {
        Apartment apartment = apartmentsService.findByIdWithInactiveStatus(id);
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


    @Operation(
            summary = "Запрос на изменение существующего апартамента",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ApartmentDto.class))
                    )
            }

    )
    @PutMapping()
    public void updateApartment(@RequestBody @Parameter(description = "Dto апартамента", required = true) ApartmentDto apartmentDto) {
        apartmentsService.update(apartmentDto);
        log.info("Апартамент изменен");
    }


    @Operation(
            summary = "Запрос на получение всех активных апартаментов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ApartmentDto.class))
                    )
            }
    )
    @GetMapping("/my_active")
    public List<ApartmentDto> getCurrentUserActiveApartments(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        log.info("Запрос для юзера " + username);
        List<ApartmentDto> apartments = apartmentsService.findActiveApartmentsByUsername(username).stream()
                .map(apartmentConverter::entityToApartmentDto).collect(Collectors.toList());
        log.info("НАШЕЛ " + apartments);
        return apartments;
    }

    @Operation(
            summary = "Запрос на получение всех неактивных апартаментов пользователя",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = ApartmentDto.class))
                    )
            }
    )
    @GetMapping("/my_inactive")
    public List<ApartmentDto> getCurrentUserInactiveApartments(@RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        log.info("Запрос для юзера " + username);
        List<ApartmentDto> apartments = apartmentsService.findInactiveApartmentsByUsername(username).stream()
                .map(apartmentConverter::entityToApartmentDto).collect(Collectors.toList());
        log.info("НАШЕЛ " + apartments);
        return apartments;
    }

    @Operation(
            summary = "Запрос на деактивацию (условное удаление) апартамента по id",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200"),
                    @ApiResponse(description = "Нет прав на выполнение запроса", responseCode = "403",
                            content = @Content(schema = @Schema(implementation = AppError.class)))
            }

    )
    @GetMapping("/deactivate/{id}")
    public void deactivateById(@PathVariable @Parameter(description = "ID апартамента", required = true) Long id, @RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        log.info("Пришел patch-запрос " + id + " username: " + username);
        apartmentsService.deactivateById(id, username);
    }

    @Operation(
            summary = "Запрос на активацию апартамента по id",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200"),
                    @ApiResponse(description = "Нет прав на выполнение запроса", responseCode = "403",
                            content = @Content(schema = @Schema(implementation = AppError.class)))
            }

    )
    @GetMapping("/activate/{id}")
    public void activateById(@PathVariable @Parameter(description = "ID апартамента", required = true) Long id, @RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        apartmentsService.activateById(id, username);
    }

    @Operation(
            summary = "Запрос на удаление (остается в БД в статусе DELETED) апартамента по id",
            responses = {
                    @ApiResponse(description = "Успешный ответ", responseCode = "200"),
                    @ApiResponse(description = "Нет прав на выполнение запроса", responseCode = "403",
                            content = @Content(schema = @Schema(implementation = AppError.class)))
            }

    )
    @GetMapping("/delete/{id}")
    public void deleteById(@PathVariable @Parameter(description = "ID апартамента", required = true) Long id, @RequestHeader @Parameter(description = "Имя пользователя", required = true) String username) {
        apartmentsService.deleteById(id, username);
    }
}
