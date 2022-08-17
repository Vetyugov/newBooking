package com.neon.new_booking.core.booking_date;

import com.neon.new_booking.api.core.BookingApartmentDtoRq;
import com.neon.new_booking.api.exceptions.ResourceNotFoundException;
import com.neon.new_booking.core.SpringWebApplication;
import com.neon.new_booking.core.services.BookingDatesService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

@Slf4j
@ContextConfiguration(classes = {SpringWebApplication.class})
@ActiveProfiles("test")
@SpringBootTest
public class BookingDatesServiceTest {
    private static BookingApartmentDtoRq bookingApartmentDtoRq1;
    private static BookingApartmentDtoRq bookingApartmentDtoRq2;
    private static final Long id = 1L;

    @Autowired
    private BookingDatesService bookingDatesService;

    @BeforeAll
    public static void createBookingApartmentDtoRq() {
        bookingApartmentDtoRq1 = new BookingApartmentDtoRq.Builder()
                .id(1L)
                .bookingStartDate(LocalDate.parse("2022-07-29"))
                .bookingFinishDate(LocalDate.parse("2022-07-30"))
                .build();

        bookingApartmentDtoRq2 = new BookingApartmentDtoRq.Builder()
                .id(2L)
                .bookingStartDate(LocalDate.parse("2022-05-13"))
                .bookingFinishDate(LocalDate.parse("2022-05-14"))
                .build();
    }

    @Test
    public void createDateOfBookingTest() {
        Assertions.assertDoesNotThrow(() -> bookingDatesService.createDateOfBooking(bookingApartmentDtoRq1));
        log.info("createDateOfBooking is success!");

        Assertions.assertThrows(ResourceNotFoundException.class, () -> bookingDatesService.createDateOfBooking(bookingApartmentDtoRq2));
        log.info("createDateOfBooking throws ResourceNotFoundException!");
    }

    @Test
    public void deleteDatesOfBookingTest(){
        Assertions.assertDoesNotThrow(() -> bookingDatesService.delete(bookingApartmentDtoRq2));
        log.info("delete is success!");

        Assertions.assertThrows(ResourceNotFoundException.class, () -> bookingDatesService.delete(bookingApartmentDtoRq1));
        log.info("delete throws ResourceNotFoundException!");
    }


}
