package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.core.BookingApartmentDtoRq;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.entities.BookingDate;
import com.geekbrains.spring.web.core.entities.Order;
import com.geekbrains.spring.web.core.repositories.ApartmentsRepository;
import com.geekbrains.spring.web.core.repositories.specifications.ApartmentsSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApartmentsService {
    private final ApartmentsRepository apartmentsRepository;
    private final BookingDatesService bookingDatesService;

    public Page<Apartment> findAll(String cityPart, Integer minPrice, Integer maxPrice, Integer minSquareMeters, Integer maxSquareMeters, Integer numberOfGuests, Integer numbersOfRooms, Integer numberOfBeds, String titlePart, String categoryPart, String startDate, String finishDate, Integer page) {
        Specification<Apartment> spec = Specification.where(null);
        if (cityPart != null) {
            spec = spec.and(ApartmentsSpecifications.cityLike(cityPart));
        }
        if (minPrice != null) {
            spec = spec.and(ApartmentsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ApartmentsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (minSquareMeters != null) {
            spec = spec.and(ApartmentsSpecifications.squareMetersGreaterOrEqualsThan(minSquareMeters));
        }
        if (maxSquareMeters != null) {
            spec = spec.and(ApartmentsSpecifications.squareMetersLessThanOrEqualsThan(maxSquareMeters));
        }
        if (numberOfGuests != null) {
            spec = spec.and(ApartmentsSpecifications.numberOfGuestsEqual(numberOfGuests));
        }
        if (numbersOfRooms != null) {
            spec = spec.and(ApartmentsSpecifications.numbersOfRoomsEqual(numbersOfRooms));
        }
        if (numberOfBeds != null) {
            spec = spec.and(ApartmentsSpecifications.numberOfBedsEqual(numberOfBeds));
        }
        if (titlePart != null) {
            spec = spec.and(ApartmentsSpecifications.titleLike(titlePart));
        }
        if (categoryPart != null) {
            spec = spec.and(ApartmentsSpecifications.categoryLike(categoryPart));
        }
        if (startDate != null & finishDate != null) {
            spec = spec.and(ApartmentsSpecifications.freeBookingDates(startDate, finishDate));
        }
        return apartmentsRepository.findAll(spec, PageRequest.of(page - 1, 8));
    }

    public List<Apartment> findAllTest(String cityPart, Integer minPrice, Integer maxPrice, Integer minSquareMeters, Integer maxSquareMeters, Integer numberOfGuests, Integer numbersOfRooms, Integer numberOfBeds, String titlePart, String categoryPart, String startDate, String finishDate) {
        Specification<Apartment> spec = Specification.where(null);
        if (cityPart != null) {
            spec = spec.and(ApartmentsSpecifications.cityLike(cityPart));
        }
        if (minPrice != null) {
            spec = spec.and(ApartmentsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ApartmentsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (minSquareMeters != null) {
            spec = spec.and(ApartmentsSpecifications.squareMetersGreaterOrEqualsThan(minSquareMeters));
        }
        if (maxSquareMeters != null) {
            spec = spec.and(ApartmentsSpecifications.squareMetersLessThanOrEqualsThan(maxSquareMeters));
        }
        if (numberOfGuests != null) {
            spec = spec.and(ApartmentsSpecifications.numberOfGuestsEqual(numberOfGuests));
        }
        if (numbersOfRooms != null) {
            spec = spec.and(ApartmentsSpecifications.numbersOfRoomsEqual(numbersOfRooms));
        }
        if (numberOfBeds != null) {
            spec = spec.and(ApartmentsSpecifications.numberOfBedsEqual(numberOfBeds));
        }
        if (titlePart != null) {
            spec = spec.and(ApartmentsSpecifications.titleLike(titlePart));
        }
        if (categoryPart != null) {
            spec = spec.and(ApartmentsSpecifications.categoryLike(categoryPart));
        }
        if (startDate != null & finishDate != null) {
            spec = spec.and(ApartmentsSpecifications.freeBookingDates(startDate, finishDate));
        }
        return apartmentsRepository.findAll(spec);
    }

    public Optional<Apartment> findById(Long id) {
        return apartmentsRepository.findById(id);
    }

    public void deleteById(Long id) {
        apartmentsRepository.deleteById(id);
    }

    public Apartment save(Apartment apartment) {
        return apartmentsRepository.save(apartment);
    }

    /*@Transactional
    public Apartment update(ApartmentDto apartmentDto) {
        Apartment apartment = apartmentsRepository.findById(apartmentDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить апартамент, не надйен в базе, id: " + apartmentDto.getId()));
        apartment.setPricePerNight(apartmentDto.getPrice());
        apartment.setTitle(apartmentDto.getTitle());
        return apartment;
    }*/
    //TODO доработать логику выбора дат бронирования
    /**
     * Добавление новых дат бронирования если они не заняты
     *
     * @param bookingApartmentDtoRq
     */

    @Transactional
    public void createDateOfBooking(BookingApartmentDtoRq bookingApartmentDtoRq) throws ResourceNotFoundException {
        LocalDate startDate = LocalDate.parse(bookingApartmentDtoRq.getBookingStartDate());
        LocalDate finishDate = LocalDate.parse(bookingApartmentDtoRq.getBookingFinishDate());
        if (bookingDatesService.checkBookingDates(bookingApartmentDtoRq.getId(), startDate, finishDate)) {
            throw new ResourceNotFoundException(String.format("В период с: %s по: %s апартаменты уже заняты!", bookingApartmentDtoRq.getBookingStartDate(), bookingApartmentDtoRq.getBookingFinishDate()));
        }
        Apartment apartment = apartmentsRepository.findById(bookingApartmentDtoRq.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить апартамент, не надйен в базе, id: " + bookingApartmentDtoRq.getId()));
        List<BookingDate> bookingDates = apartment.getBookingDates();
        BookingDate bookingDate = new BookingDate();
        bookingDate.setApartment(apartment);
        bookingDate.setStartDate(startDate);
        bookingDate.setFinishDate(finishDate);
        bookingDates.add(bookingDate);
        apartment.setBookingDates(bookingDates);
        apartmentsRepository.save(apartment);
    }

    public List<Apartment> findApartmentsByUsername(String username) {
        return apartmentsRepository.findAllByUsername(username);
    }

}
