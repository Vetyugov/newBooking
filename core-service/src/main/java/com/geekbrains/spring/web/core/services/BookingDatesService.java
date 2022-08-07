package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.core.BookingApartmentDtoRq;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.entities.BookingDate;
import com.geekbrains.spring.web.core.repositories.BookingDatesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingDatesService {
    private final BookingDatesRepository bookingDatesRepository;
    private final ApartmentsService apartmentsService;

   /* public boolean checkBookingDates(Long apartmentId, LocalDate startDate, LocalDate finishDate) {
        return bookingDatesRepository.existsBookingDates(apartmentId, startDate, finishDate);
    }*/


    //TODO доработать логику выбора дат бронирования
    /**
     * Добавление новых дат бронирования если они не заняты
     *
     * @param bookingApartmentDtoRq
     */

    @Transactional
    public void createDateOfBooking(BookingApartmentDtoRq bookingApartmentDtoRq) throws ResourceNotFoundException {
        log.info("Создание дней заказа " + bookingApartmentDtoRq);
        Apartment apartment = apartmentsService.findByIdWithActiveStatus(bookingApartmentDtoRq.getId());
        if (bookingDatesRepository.existsBookingDates(bookingApartmentDtoRq.getId(), bookingApartmentDtoRq.getBookingStartDate(), bookingApartmentDtoRq.getBookingFinishDate())){
            throw new ResourceNotFoundException(String.format("В период с: %s по: %s апартаменты уже заняты!", bookingApartmentDtoRq.getBookingStartDate(), bookingApartmentDtoRq.getBookingFinishDate()));
        }
        BookingDate bookingDate = new BookingDate();
        bookingDate.setApartment(apartment);
        bookingDate.setStartDate(bookingApartmentDtoRq.getBookingStartDate());
        bookingDate.setFinishDate(bookingApartmentDtoRq.getBookingFinishDate());
        bookingDatesRepository.save(bookingDate);
        log.info("Created new bookingDate = " + bookingDate);
    }
    @Transactional
    public void delete(BookingApartmentDtoRq bookingApartmentDtoRq) throws ResourceNotFoundException {
        log.info("Удаление дней заказа " + bookingApartmentDtoRq);
       BookingDate bookingDate = bookingDatesRepository.findBookingDateByApartmentIdAndStartDateAndFinishDate(
                bookingApartmentDtoRq.getId(),
                bookingApartmentDtoRq.getBookingStartDate(),
                bookingApartmentDtoRq.getBookingFinishDate()).orElseThrow(() -> new ResourceNotFoundException("BookingDates not found, apartmentId: " + bookingApartmentDtoRq.getId()));
        log.info("Id bookingDate " + bookingDate.getId());
       bookingDatesRepository.delete(bookingDate);
        log.info("Id bookingDate " + bookingDate.getId() + " удален");
    }

}
