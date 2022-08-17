package com.neon.new_booking.api.bookings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

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

    public BookingDto(List<BookingItemDto> items) {
        this.items = items;
    }


}
