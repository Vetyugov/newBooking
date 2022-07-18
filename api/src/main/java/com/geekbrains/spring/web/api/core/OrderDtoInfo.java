package com.geekbrains.spring.web.api.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель заказа")
@ToString
@Builder
public class OrderDtoInfo {
    @Schema(description = "Идентификатор", example = "123")
    private Long id;

    @Size(min = 1, max = 255, message = "максимум 255 символов")
    @Schema(description = "Уникальное имя пользователя(хозяина)", example = "aspushkin", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректное имя пользователя")
    private String username;

    @Schema(description = "id жильz")
    private Long apartmentId;

    @Schema(description = "Описание жилья")
    private String apartmentTitle;


    @Schema(description = "Дата заселения", example = "2022-01-26", required = true)
    @Pattern(regexp = "\\d{4}(-)\\d{2}(-)\\d{2}", message = "Некорректно указана дата заселения")
    private LocalDate apartmentCheckIn;

    @Schema(description = "Дата выселения", example = "2022-01-26", required = true)
    @Pattern(regexp = "\\d{4}(-)\\d{2}(-)\\d{2}", message = "Некорректно указана дата выселения")
    private LocalDate apartmentCheckOut;

    @Schema(description = "Цена за одну ночь проживания", example = "1528.40", required = true)
    private BigDecimal price;

    @Schema(description = "Полная стоимость проживания", example = "3056.80", required = true)
    private BigDecimal totalPrice;

    @Schema(description = "Статус заказа")
    private String status;

}
