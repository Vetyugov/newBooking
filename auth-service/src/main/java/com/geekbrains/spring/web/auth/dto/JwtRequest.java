package com.geekbrains.spring.web.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Schema(description = "Запрос на авторизацию")
public class JwtRequest {
    @NotNull
    @Size(max = 36)
    @Schema(description = "Логин пользователя", example = "Alex")
    private String username;

    @Schema(description = "Пароль пользователя", example = "123")
    @NotNull
    @Size(min = 3, max = 12)
    private String password;
}
