package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.entities.BookingDate;
import com.geekbrains.spring.web.core.repositories.BookingDatesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookingDatesService {
    private final BookingDatesRepository bookingDatesRepository;

    public boolean checkBookingDates(Long apartmentId, LocalDate startDate, LocalDate finishDate) {
        return bookingDatesRepository.existsBookingDates(apartmentId, startDate, finishDate);
    }

    public BookingDate save(BookingDate bookingDate) {
        return bookingDatesRepository.save(bookingDate);
    }
}
