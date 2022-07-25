package com.geekbrains.spring.web.core.apartment;

import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.SpringWebApplication;
import com.geekbrains.spring.web.core.entities.Address;
import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.entities.ApartmentCategory;
import com.geekbrains.spring.web.core.services.ApartmentsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@ContextConfiguration(classes = {SpringWebApplication.class})
@ActiveProfiles("test")
@SpringBootTest
public class ApartmentsServiceTest {
    private static Apartment firstApartment;
    private static Apartment secondApartment;
    private static Long id = 1L;

    @Autowired
    private ApartmentsService apartmentsService;

    @BeforeAll
    public static void createApartment(){
        ApartmentCategory apartmentCategory = new ApartmentCategory();
        apartmentCategory.setId(1l);
        apartmentCategory.setTitle("Квартира");
        Address address = new Address("Москва", "Симоновский вал", 5);
        Address address1 = new Address("Санкт-Петербург", "Петроградская наб.", 34);
        firstApartment = new Apartment.Builder()
                .id(1l)
                .title("Самая лучшая квартира")
                .apartmentCategory(apartmentCategory)
                .address(address)
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
                .address(address1)
                .squareMeters(75)
                .numberOfGuests(5)
                .numberOfRooms(3)
                .numberOfBeds(5)
                .pricePerNight(new BigDecimal("10500.00"))
                .build();
    }
    @Test
    public void findAllTest(){
        List<Apartment> apartments = apartmentsService.findAllTest("Мос", null, 5000,20,50, 3, null, null, null, "Кварт","2022-07-29", "2022-07-30");
        Assertions.assertEquals(1, apartments.size());
        log.info("Нет пересечения интервалов ни в одном из объектов");


        List<Apartment> apartments1 = apartmentsService.findAllTest(null, null,null,null,null, null, null, null, null, null,"2022-07-05", "2022-07-30");
        Assertions.assertEquals(2, apartments1.size());
        log.info("Дата начала входит в имеющийся интервал");
        log.info(apartments1.get(0).getTitle());
        log.info(apartments1.get(1).getTitle());

        List<Apartment> apartments2 = apartmentsService.findAllTest(null, null,null,null,null, null, null, null,null,"Апарт","2022-05-13", "2022-05-16");
        Assertions.assertEquals(1, apartments2.size());
        log.info("Совпадение дат начала разных интервалов");
        log.info(apartments2.get(0).getTitle());

        List<Apartment> apartments3 = apartmentsService.findAllTest("Простоква", null,null,null,null, null, null, null, null, null,"2022-05-13", "2022-07-16");
        Assertions.assertEquals(1, apartments3.size());
        log.info(apartments2.get(0).getTitle());

        List<Apartment> apartments4 = apartmentsService.findAllTest(null, null,11000,34,null, null, null, null, null, null,"2022-07-09", "2022-07-30");
        Assertions.assertEquals(3, apartments4.size());
        log.info("Совпадение даты начала и даты конца разных интервалов");
        log.info(apartments4.get(0).getTitle());
        log.info(apartments4.get(1).getTitle());
        log.info(apartments4.get(2).getTitle());


        List<Apartment> apartments5 = apartmentsService.findAllTest(null, null,null,null,null, null, null, null, null, "Апарт",null, null);
        Assertions.assertEquals(2, apartments5.size());
        log.info("Нашлось два объекта в категории: квартира ");
        log.info(apartments4.get(0).getTitle());
        log.info(apartments4.get(1).getTitle());

        List<Apartment> apartments6 = apartmentsService.findAllTest(null, null,null,null,null, null, null, null, null, null,"2022-07-29", "2022-07-30");
        Assertions.assertEquals(3, apartments6.size());
        log.info("Нет пересечения интервалов ни в одном из объектов. Другие параметры поиска не заданы");
    }

    @Test
    public void findByIdTest() {
        Apartment apartment = apartmentsService.findById(id).orElseThrow(()-> new ResourceNotFoundException("Аппартамент с id: " + id + " не найден!"));
        Assertions.assertEquals("Квартира", apartment.getApartmentCategory().getTitle());
    }


}
