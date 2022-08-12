package com.geekbrains.spring.web.api.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Schema(description = "Модель для создания и отображения адреса апартамента")
@NoArgsConstructor
public class AddressDto {

    @Schema(description = "Название города", required = true, example = "Москва")
    private String city;

    @Schema(description = "Назавние улицы", required = true, example = "Ленина")
    private String street;

    @Schema(description = "Номер дома", required = true, example = "4")
    private Integer buildingNumber;


    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public Integer getBuildingNumber() {
        return buildingNumber;
    }

    public AddressDto(String city, String street, Integer buildingNumber) {
        this.city = city;
        this.street = street;
        this.buildingNumber = buildingNumber;
    }


}
