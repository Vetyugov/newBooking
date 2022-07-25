package com.geekbrains.spring.web.api.bookings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Модель для отображения апартаментов на фронте")
@ToString

public class BookingItemDto {
    @Schema(description = "Индентификатор позиции")
    @NotBlank(message = "Выберите жилье. Обязательное поле")
    private Long itemId;

    @Schema(description = "Выбранное жилье")
    @NotBlank(message = "Выберите жилье. Обязательное поле")
    private Long apartmentId;

    @Schema(description = "Информация об апартаменте. Категория, Название, кол-во комнат, кол-во мест")
    @NotBlank(message = "Выберите информацию. Обязательное поле")
    private String apartmentInfo;

    @Schema(description = "Адрес апартамента. Город, Улица, Номер дома")
    @NotBlank(message = "Выберите адрес. Обязательное поле")
    private String apartmentAddress;

    @Schema(description = "Дата заселения", example = "2022-01-26", required = true)
    @NotBlank(message = "Укажите дату заселения. Обязательное поле")
    @Pattern(regexp = "\\d{4}(-)\\d{2}(-)\\d{2}", message = "Некорректно указана дата заселения")
    private String bookingStartDate;

    @Schema(description = "Дата выселения", example = "2022-01-26", required = true)
    @NotBlank(message = "Укажите дату выселения. Обязательное поле")
    @Pattern(regexp = "\\d{4}(-)\\d{2}(-)\\d{2}", message = "Некорректно указана дата выселения")
    private String bookingFinishDate;

    @Schema(description = "Продолжительность бронирования в сутках (в ночах)", required = true)
    @NotBlank(message = "Укажите продолжительность бронирования. Обязательное поле")
    private Integer bookingDuration;

    //цена за ночь
    @Schema(description = "Цена за одну ночь проживания", example = "1528.40", required = true)
    @NotBlank(message = "Укажите цену за одну ночь проживания. Обязательное поле")
    private BigDecimal pricePerNight;

    //Цена за период
    @Schema(description = "Полная стоимость проживания за аппартамент", example = "3056.80", required = true)
    @NotBlank(message = "Укажите полную стоимоть проживания. Обязательное поле")
    private BigDecimal pricePerOrder;

    @Schema(description = "Селектор выбора для расчёта общей стоимости апартаметов в списке", required = true)
    @NotBlank(message = "ДА/НЕТ. Цену за период учитывать при расчёте Общей стоимости. По умолчанию ДА")
    private Boolean selector;

    public BookingItemDto(
            Long itemId,
            Long apartmentId,
            String apartmentInfo,
            String apartmentAddress,
            String bookingStartDate,
            String bookingFinishDate,
            Integer bookingDuration,
            BigDecimal pricePerNight,
            BigDecimal pricePerOrder
    ) {
        this.itemId = itemId;
        this.apartmentId = apartmentId;
        this.apartmentInfo = apartmentInfo;
        this.apartmentAddress = apartmentAddress;
        this.bookingStartDate = bookingStartDate;
        this.bookingFinishDate = bookingFinishDate;
        this.bookingDuration = bookingDuration;
        this.pricePerNight = pricePerNight;
        this.pricePerOrder = pricePerOrder;
    }
}
