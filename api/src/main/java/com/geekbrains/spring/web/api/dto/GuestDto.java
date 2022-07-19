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
public class GuestDto {//добавление информации в ЛК, отображение данных

    @Schema(description = "Идентификатор", example = "123")
    Long id;

    @Schema(description = "Идентификатор юзера", example = "123")
    Long userId;

    @Schema(description = "Имя", example = "Александр")
    String name;

    @Schema(description = "Отчество", example = "Сергеевич")
    String patronymic;

    @Schema(description = "Фамилия", example = "Пушкин")
    String surname;

    @NotBlank(message = "логин. Обязательное поле")
    @Size(min = 1, max = 255, message = "максимум 255 символов")
    @Schema(description = "Уникальное имя пользователя", example = "pushkin", required = true)
    @Pattern(regexp = ".+[a-zA-Z0-9]", message = "Некорректное имя пользователя")
    String username;

    public GuestDto(Long id, Long userId, String name, String patronymic, String surname, String username) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.patronymic = patronymic;
        this.surname = surname;
        this.username = username;
    }
}
