package com.neon.new_booking.core.booking_date;

import com.neon.new_booking.core.SpringWebApplication;
import com.neon.new_booking.core.repositories.BookingDatesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

@DataJpaTest
//тест запускается только если загружать контекст всего МС. Почему?
// Как сделать так, чтобы загружать только используемые классы?
@ContextConfiguration(classes = {SpringWebApplication.class})
@ActiveProfiles("test")
public class BookingDatesRepositoryTest {
    private static final Long apartmentId = 1L;
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
    private BookingDatesRepository bookingDatesRepository;

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
    public void existsBookingDatesTest() {
        Assertions.assertTrue(bookingDatesRepository.existsBookingDates(apartmentId, firstStart, firstFinish));
        Assertions.assertTrue(bookingDatesRepository.existsBookingDates(apartmentId, secondStart, secondFinish));
        Assertions.assertTrue(bookingDatesRepository.existsBookingDates(apartmentId, thirdStart, thirdFinish));
        Assertions.assertFalse(bookingDatesRepository.existsBookingDates(apartmentId, foursStart, foursFinish));
        Assertions.assertFalse(bookingDatesRepository.existsBookingDates(apartmentId, fifthStart, fifthFinish));
        Assertions.assertFalse(bookingDatesRepository.existsBookingDates(apartmentId, sixthStart, sixthFinish));
    }

}
