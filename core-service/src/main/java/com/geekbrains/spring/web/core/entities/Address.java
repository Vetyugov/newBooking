package com.geekbrains.spring.web.core.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Column (name = "city", nullable = false)
    private String city;

    @Column (name = "street", nullable = false)
    private String street;

    @Column (name = "building_number", nullable = false)
    private Integer buildingNumber;
}
