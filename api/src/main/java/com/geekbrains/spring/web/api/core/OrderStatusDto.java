package com.geekbrains.spring.web.api.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Статус заказа")
@ToString
@Data
@Builder
public class OrderStatusDto {
    @Schema(description = "Идентификатор", example = "123")
    private Long id;

    @Schema(description = "Описание статуса заказа", example = "booked")
    private String description;
}
