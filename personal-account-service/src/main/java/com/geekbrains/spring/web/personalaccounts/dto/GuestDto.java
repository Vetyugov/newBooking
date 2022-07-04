package com.geekbrains.spring.web.personalaccounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)//все поля приватные
@Schema(description = "Модель пользователя")
@ToString
@NoArgsConstructor
@Builder
public class GuestDto {

    @Schema(description = "Идентификатор", example = "123")
    Long id;

    @Schema(description = "Имя", example = "Александр")
    String name;

    @Schema(description = "Отчество", example = "Сергеевич")
    String patronymic;

    @Schema(description = "Фамилия", example = "Пушкин")
    String surname;

    @NotBlank(message = "адрес электронной почты. Обязательное поле")
    @Size(min=1, max = 255, message = "максимум 255 символов")
    @Schema(description = "E-mail", example = "aspushkin@gmail.com", required = true)
    @Pattern(regexp = ".+@.+.[a-zA-Z0-9]", message = "Некорректный адрес электронной почты")
    String email;

    @NotBlank(message = "логин. Обязательное поле")
    @Size(min = 1, max = 255, message = "максимум 255 символов")
    @Schema(description = "Уникальное имя пользователя", example = "pushkin", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректное имя пользователя")
    String username;

    @NotBlank(message = "пароль. Обязательное поле")
    @Size(min = 3, max = 12, message = "Пароль должен быть от 3х до 12-ти символов")
    @Schema(description = "Пароль пользователя", example = "123456", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректный пароль")
    String password;

//    @Schema(description = "Список ролей пользователя", implementation = List.class)// по этой теме есть вопросы для обсуждения с командой
//    Collection<String> roles;
    public GuestDto(Long id, String name, String patronymic, String surname, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
