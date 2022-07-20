package com.geekbrains.spring.web.api.bookings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;
@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Список объектов бронирования")
@ToString
public class BookingDto {

    @Schema(description = "Список объектов бронирования")
    private List<BookingItemDto> items;

    @Schema(description = "Цена за весь список бронирования", example = "1528.40", required = true)
    private BigDecimal totalPrice;
    
    public BookingDto(List<BookingItemDto> items) {
        this.items = items;
        this.totalPrice = calculate();
    }

    private BigDecimal calculate() {
        BigDecimal sum = BigDecimal.ZERO;
        for (BookingItemDto myItem : items) {
            if (myItem.getSelector()){
                sum = sum.add(myItem.getPricePerOrder());
            }
        }
        return sum;
    }
}
