package com.geekbrains.spring.web.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Обертка для отправки строкового значения в виде JSON")
public class JwtResponse {
    @Schema(description = "Значение")
    private String token;

    private String role;
}
