package com.geekbrains.spring.web.api.dto;

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
@FieldDefaults(level = AccessLevel.PRIVATE)//все поля приватные
@Schema(description = "Модель пользователя")
@ToString
@NoArgsConstructor
@Builder
public class LegalHostDto {//добавление информации в ЛК, отображение данных

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

    @NotBlank(message = "название компании или ИП")
    @Size(min = 1, max = 255, message = "максимум 255 символов")
    @Schema(description = "Название компании/ИП (хозяина)", example = "ИП А.С. Пушкин", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректно введено название")
    String titleFirm;

    @NotBlank(message = "страна. Обязательное поле")
    @Size(min = 1, max = 255, message = "максимум 255 символов")
    @Schema(description = "Страна пользователя (хозяина)", example = "Russia", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректно указана страна")
    String country;

    @NotBlank(message = "адрес офиса. Обязательное поле")
    @Size(min = 1, max = 255, message = "максимум 255 символов")
    @Schema(description = "Адрес офиса пользователя (хозяина)", example = "Москва, Нахимовский проспект, д 61, к 30", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректно указан адрес офиса")
    String officeAddress;

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

    @NotBlank(message = "номер счета. Обязательное поле")
    @Size(min = 16, max = 20, message = "Номер счета должен содержать от 16 до 20 символов")
    @Schema(description = "Номер счета пользователя (хозяина)", example = "55123456785434346789", required = true)
    @Pattern(regexp = ".+[Z0-9]", message = "Не корректно введен почтовый индекс")
    String account;

    public LegalHostDto(Long id, Long userId, String name, String patronymic, String surname, String username, String titleFirm, String country, String officeAddress, String postcode, Integer inn, String account) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.username = username;
        this.titleFirm = titleFirm;
        this.country = country;
        this.officeAddress = officeAddress;
        this.postcode = postcode;
        this.inn = inn;
        this.account = account;
    }
}