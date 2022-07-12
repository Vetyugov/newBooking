package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.core.BookingApartmentDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.entities.BookingDate;
import com.geekbrains.spring.web.core.repositories.ApartmentsRepository;
import com.geekbrains.spring.web.core.repositories.specifications.ApartmentsSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApartmentsService {
    private final ApartmentsRepository apartmentsRepository;

    public Page<Apartment> findAll(Integer minPrice, Integer maxPrice, String titlePart, String categoryPart, Integer page) {
        Specification<Apartment> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ApartmentsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ApartmentsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (titlePart != null) {
            spec = spec.and(ApartmentsSpecifications.titleLike(titlePart));
        }
        if (categoryPart != null) {
            spec = spec.and(ApartmentsSpecifications.categoryLike(categoryPart));
        }

        return apartmentsRepository.findAll(spec, PageRequest.of(page - 1, 8));
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

    @Transactional
    public Integer createDateOfBooking(BookingApartmentDto bookingApartmentDto) {
        LocalDateTime startDate = LocalDateTime.parse(bookingApartmentDto.getBookingStartDate());
        LocalDateTime finishDate = LocalDateTime.parse(bookingApartmentDto.getBookingFinishDate());
        Apartment apartment = apartmentsRepository.findById(bookingApartmentDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить апартамент, не надйен в базе, id: " + bookingApartmentDto.getId()));
        List<BookingDate> bookingDates = apartment.getBookingDates();
        LocalDateTime currentDate = startDate;
        Integer numberOfBookedDays = 0;
        while(!currentDate.equals(finishDate)){
            LocalDateTime finalCurrentDate = currentDate;
            BookingDate bookingDate = new BookingDate();
            bookingDate.setApartment(apartment);
            bookingDate.setDate(finalCurrentDate);
            bookingDates.add(bookingDate);
            numberOfBookedDays++;
            currentDate = finalCurrentDate.plusDays(1L);
        }
        apartment.setBookingDates(bookingDates);
        apartmentsRepository.save(apartment);
        return numberOfBookedDays;
    }

}
