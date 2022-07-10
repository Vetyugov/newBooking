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
public class LegalHostDto {

    @Schema(description = "Идентификатор", example = "123")
    Long id;

    @Schema(description = "Имя хозяина", example = "Александр")
    String name;

    @Schema(description = "Отчество хозяина", example = "Сергеевич")
    String patronymic;

    @Schema(description = "Фамилия хозяина", example = "Пушкин")
    String surname;

    @NotBlank(message = "адрес электронной почты. Обязательное поле")
    @Size(min=1, max = 255, message = "максимум 255 символов")
    @Schema(description = "E-mail", example = "aspushkin@gmail.com", required = true)
    @Pattern(regexp = ".+@.+.[a-zA-Z0-9]", message = "Некорректный адрес электронной почты")
    String email;

    @NotBlank(message = "логин. Обязательное поле")
    @Size(min = 1, max = 255, message = "максимум 255 символов")
    @Schema(description = "Уникальное имя пользователя(хозяина)", example = "aspushkin", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректное имя пользователя")
    String username;

    @NotBlank(message = "пароль. Обязательное поле")
    @Size(min = 3, max = 12, message = "Пароль должен быть от 3х до 12-ти символов")
    @Schema(description = "Пароль пользователя(хозяина)", example = "123456", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректный пароль")
    String password;

//    @NotBlank(message = "Подтвердите пароль. Обязательное поле")
//    @Size(min = 3, max = 16, message = "Пароль должен быть от 3х до 16-ти символов")
//    @Schema(description = "Подтверждение пароля пользователя(хозяина)", example = "123456", required = true)
//    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректное подтверждение пароля")
//    String passwordConfirmation;

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
    Integer postcode;

    @NotBlank(message = "ИНН. Обязательное поле")
    @Size(min = 10, max = 10, message = "ИНН должен содержать 10 символов")
    @Schema(description = "ИНН пользователя", example = "123456789101", required = true)
    @Pattern(regexp = ".+[Z0-9]", message = "Не корректно введен ИНН")
    Integer inn;

    @NotBlank(message = "номер счета. Обязательное поле")
    @Size(min = 16, max = 20, message = "Номер счета должен содержать от 16 до 20 символов")
    @Schema(description = "Номер счета пользователя (хозяина)", example = "55123456785434346789", required = true)
    @Pattern(regexp = ".+[Z0-9]", message = "Не корректно введен почтовый индекс")
    Integer account;

//    @Schema(description = "Список ролей пользователя(хозяина)", implementation = List.class)// по этой теме есть вопросы
//    Collection<String> roles;

    public LegalHostDto(Long id, String name, String patronymic, String surname, String email, String username, String password, String titleFirm, String country, String officeAddress, Integer postcode, Integer inn, Integer account) {
        this.id = id;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.titleFirm = titleFirm;
        this.country = country;
        this.officeAddress = officeAddress;
        this.postcode = postcode;
        this.inn = inn;
        this.account = account;
    }
}