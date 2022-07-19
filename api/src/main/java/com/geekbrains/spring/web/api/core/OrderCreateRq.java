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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель для создания заказа")
@ToString
@Builder
public class OrderCreateRq {

        @NotBlank(message = "Введите свой логин. Обязательное поле")
        @Size(min = 1, max = 255, message = "максимум 255 символов")
        @Schema(description = "Уникальное имя пользователя(хозяина)", example = "aspushkin", required = true)
        @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректное имя пользователя")
        private String username;

        @Schema(description = "Выбранное жилье")
        @NotBlank(message = "Выберите жилье. Обязательное поле")
        private Long apartmentId;

        @Schema(description = "Дата заселения", example = "2022-01-26", required = true)
        @NotBlank(message = "Укажите дату заселения. Обязательное поле")
        @Pattern(regexp = "\\d{4}(-)\\d{2}(-)\\d{2}", message = "Некорректно указана дата заселения")
        private LocalDate bookingStartDate;

        @Schema(description = "Дата выселения", example = "2022-01-26", required = true)
        @NotBlank(message = "Укажите дату выселения. Обязательное поле")
        @Pattern(regexp = "\\d{4}(-)\\d{2}(-)\\d{2}", message = "Некорректно указана дата выселения")
        private LocalDate bookingFinishDate;

        //цена за ночь
        @Schema(description = "Цена за одну ночь проживания", example = "1528.40", required = true)
        @NotBlank(message = "Укажите цену за одну ночь проживания. Обязательное поле")
        private BigDecimal pricePerNight;

        //Цена за период
        @Schema(description = "Полная стоимость проживания за аппартамент", example = "3056.80", required = true)
        @NotBlank(message = "Укажите полную стоимоть проживания. Обязательное поле")
        private BigDecimal pricePerOrder;

//        @Schema(description = "Статус заказа")
//        @NotBlank(message = "Укажите статус заказа. Обязательное поле")
//        private OrderStatusDto status;
}
