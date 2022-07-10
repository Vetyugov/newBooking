package com.geekbrains.spring.web.api.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileDto {

    @Schema(description = "Идентификатор", example = "123")
    Long id;

    @NotBlank(message = "логин. Обязательное поле")
    @Size(min = 1, max = 255, message = "максимум 255 символов")
    @Schema(description = "Уникальное имя пользователя", example = "pushkin", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректное имя пользователя")
    String username;

    @NotBlank(message = "пароль. Обязательное поле")
    @Size(min = 3, max = 12, message = "Пароль должен быть от 3х до 12-ти символов")
    @Schema(description = "Пароль пользователя(хозяина)", example = "123456", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректный пароль")
    String password;

    @NotBlank(message = "Подтвердите пароль. Обязательное поле")
    @Size(min = 3, max = 12, message = "Пароль должен быть от 3х до 12-ти символов")
    @Schema(description = "Подтверждение пароля пользователя", example = "123456", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректное подтверждение пароля")
    String passwordConfirmation;

    @NotBlank(message = "адрес электронной почты. Обязательное поле")
    @Size(min=1, max = 255, message = "максимум 255 символов")
    @Schema(description = "E-mail", example = "aspushkin@gmail.com", required = true)
    @Pattern(regexp = ".+@.+.[a-zA-Z0-9]", message = "Некорректный адрес электронной почты")
    String email;

    @Schema(description = "Имя", example = "Александр")
    String name;

    @Schema(description = "Отчество", example = "Сергеевич")
    String patronymic;

    @Schema(description = "Фамилия", example = "Пушкин")
    String surname;

    @Schema(description = "Список ролей пользователя", implementation = List.class)
    Collection<String> roles;

    public ProfileDto(String username) {
        this.username = username;
    }
    public ProfileDto(Long id, String username, String password, String email, String name, String patronymic, String surname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
    }
}
