package com.geekbrains.spring.web.core.apartment;

import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.entities.ApartmentCategory;
import com.geekbrains.spring.web.core.services.ApartmentsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@Slf4j
@SpringBootTest
public class ApartmentsServiceTest {
    private static Apartment firstApartment;
    private static Apartment secondApartment;

    @Autowired
    private ApartmentsService apartmentsService;

    @BeforeAll
    public static void createApartment(){
        ApartmentCategory apartmentCategory = new ApartmentCategory();
        apartmentCategory.setId(1l);
        apartmentCategory.setTitle("Квартира");

        firstApartment = new Apartment.Builder()
                .id(1l)
                .title("Самая лучшая квартира")
                .apartmentCategory(apartmentCategory)
                .city("Москва")
                .street("Симоновский вал")
                .buildingNumber(5)
                .squareMeters(45)
                .numberOfGuests(3)
                .numberOfRooms(2)
                .numberOfBeds(3)
                .pricePerNight(new BigDecimal("3500.00"))
                .build();

        secondApartment = new Apartment.Builder()
                .id(2l)
                .title("Квартира с видом на реку")
                .apartmentCategory(apartmentCategory)
                .city("Санкт-Петербург")
                .street("Петроградская наб.")
                .buildingNumber(34)
                .squareMeters(75)
                .numberOfGuests(5)
                .numberOfRooms(3)
                .numberOfBeds(5)
                .pricePerNight(new BigDecimal("10500.00"))
                .build();
    }

}
