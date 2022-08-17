package com.neon.new_booking.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель пользователя")
@ToString
@NoArgsConstructor
public class IndividualHostDto {//добавление информации в ЛК, отображение данных

    @Schema(description = "Идентификатор", example = "123")
    Long id;

    @Schema(description = "Идентификатор юзера", example = "123")
    Long userId;

    @Schema(description = "Имя хозяина", example = "Александр")
    String name;

    @Schema(description = "Отчество хозяина", example = "Сергеевич")
    String patronymic;

    @Schema(description = "Фамилия хозяина", example = "Пушкин")
    String surname;

    @NotBlank(message = "логин. Обязательное поле")
    @Size(min = 1, max = 255, message = "максимум 255 символов")
    @Schema(description = "Уникальное имя пользователя(хозяина)", example = "aspushkin", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректное имя пользователя")
    String username;

    @NotBlank(message = "страна. Обязательное поле")
    @Size(min = 1, max = 255, message = "максимум 255 символов")
    @Schema(description = "Страна пользователя (хозяина)", example = "Russia", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректно указана страна")
    String country;

    @NotBlank(message = "Адрес. Обязательное поле")
    @Size(min = 1, max = 255, message = "максимум 255 символов")
    @Schema(description = "Адрес пользователя (хозяина)", example = "Москва, Нахимовский проспект, д 61, к 30", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректно указан адрес")
    String address;

    @NotBlank(message = "почтовый индекс. Обязательное поле")
    @Size(min = 5, max = 6, message = "Индекс должен содержать от 5 до 6 символов")
    @Schema(description = "почтовый индекс пользователя (хозяина)", example = "23100", required = true)
    @Pattern(regexp = ".+[Z0-9]", message = "Не корректно введен почтовый индекс")
    String postcode;

    @NotBlank(message = "ИНН. Обязательное поле")
    @Size(min = 10, max = 10, message = "ИНН должен содержать 10 символов")
    @Schema(description = "ИНН пользователя", example = "123456789101", required = true)
    @Pattern(regexp = ".+[Z0-9]", message = "Не корректно введен ИНН")
    Integer inn;

    @NotBlank(message = "Укажите номер счета. Обязательное поле")
    @Size(min = 16, max = 20, message = "Номер счета должен содержать от 16 до 20 символов")
    @Schema(description = "Номер счета пользователя (хозяина)", example = "55123456785434346789", required = true)
    @Pattern(regexp = ".+[Z0-9]", message = "Не корректно введен почтовый индекс")
    String account;

    public IndividualHostDto(Long id, Long userId, String name, String patronymic, String surname, String username, String country, String address, String postcode, Integer inn, String account) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.username = username;
        this.country = country;
        this.address = address;
        this.postcode = postcode;
        this.inn = inn;
        this.account = account;
    }
}