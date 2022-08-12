package com.geekbrains.spring.web.core.apartment;

import com.geekbrains.spring.web.core.SpringWebApplication;
import com.geekbrains.spring.web.core.repositories.ApartmentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

@DataJpaTest
@ContextConfiguration(classes = {SpringWebApplication.class}) //тест запускается только если загружать контекст всего МС. Почему? Как исправить?
@ActiveProfiles("test")
@Slf4j
public class ApartmentsRepositoryTest {
    private static LocalDate firstStart;
    private static LocalDate firstFinish;
    private static LocalDate secondStart;
    private static LocalDate secondFinish;
    private static LocalDate thirdStart;
    private static LocalDate thirdFinish;
    private static LocalDate foursStart;
    private static LocalDate foursFinish;
    private static LocalDate fifthStart;
    private static LocalDate fifthFinish;
    private static LocalDate sixthStart;
    private static LocalDate sixthFinish;

    @Autowired
    private ApartmentsRepository apartmentsRepository;

    @BeforeAll
    public static void initDates() {
       firstStart = LocalDate.parse("2022-07-05");
       firstFinish = LocalDate.parse("2022-07-09");
       secondStart = LocalDate.parse("2022-06-01");
       secondFinish = LocalDate.parse("2022-06-14");
       thirdStart = LocalDate.parse("2022-07-04");
       thirdFinish = LocalDate.parse("2022-07-08");
       foursStart = LocalDate.parse("2022-07-02");
       foursFinish = LocalDate.parse("2022-07-04");
       fifthStart = LocalDate.parse("2022-07-29");
       fifthFinish = LocalDate.parse("2022-07-30");
       sixthStart = LocalDate.parse("2022-05-13");
       sixthFinish = LocalDate.parse("2022-05-14");
    }


    @Test
    public void findFilteredByBookingDatesTest() {
        Assertions.assertEquals(1, apartmentsRepository.findFilteredApartments(firstStart, firstFinish).size());
        log.info(apartmentsRepository.findFilteredApartments(firstStart, firstFinish).get(0).getId().toString());

        Assertions.assertEquals(1,apartmentsRepository.findFilteredApartments(secondStart, secondFinish).size());
        log.info(apartmentsRepository.findFilteredApartments(secondStart, secondFinish).get(0).getId().toString());

        Assertions.assertEquals(1, apartmentsRepository.findFilteredApartments(thirdStart, thirdFinish).size());
        log.info(apartmentsRepository.findFilteredApartments(thirdStart, thirdFinish).get(0).getId().toString());

        Assertions.assertEquals(2, apartmentsRepository.findFilteredApartments(foursStart, foursFinish).size());
        log.info(apartmentsRepository.findFilteredApartments(foursStart, foursFinish).get(0).getTitle());
        log.info(apartmentsRepository.findFilteredApartments(foursStart, foursFinish).get(1).getTitle());

        Assertions.assertEquals(2, apartmentsRepository.findFilteredApartments(fifthStart, fifthFinish).size());
        log.info(apartmentsRepository.findFilteredApartments(fifthStart, fifthFinish).get(0).getTitle());
        log.info(apartmentsRepository.findFilteredApartments(fifthStart, fifthFinish).get(1).getTitle());

        Assertions.assertEquals(1, apartmentsRepository.findFilteredApartments(sixthStart, sixthFinish).size());
        log.info(apartmentsRepository.findFilteredApartments(sixthStart, sixthFinish).get(0).getTitle());

    }

}
