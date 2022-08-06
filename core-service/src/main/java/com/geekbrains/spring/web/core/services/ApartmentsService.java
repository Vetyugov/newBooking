package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.entities.ApartmentStatus;
import com.geekbrains.spring.web.core.exceptions.ResourceIsForbiddenException;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApartmentsService {
    private final ApartmentsRepository apartmentsRepository;

    public Page<Apartment> findAll(String cityPart, Integer minPrice, Integer maxPrice, Integer minSquareMeters,
                                   Integer maxSquareMeters, Integer numberOfGuests, Integer numbersOfRooms,
                                   Integer numberOfBeds, String titlePart, String categoryPart,
                                   String startDate, String finishDate, Integer page) {
        Specification<Apartment> spec = Specification.where(null);
        spec = spec.and(ApartmentsSpecifications.statusEqual());

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
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate.substring(0, startDate.indexOf("T")), pattern);
            LocalDate finish = LocalDate.parse(finishDate.substring(0, finishDate.indexOf("T")), pattern);
            spec = spec.and(ApartmentsSpecifications.freeBookingDates(start, finish));
        }
        return apartmentsRepository.findAll(spec, PageRequest.of(page - 1, 8));
    }

    public List<Apartment> findAllTest(String cityPart, Integer minPrice, Integer maxPrice, Integer minSquareMeters, Integer maxSquareMeters, Integer numberOfGuests, Integer numbersOfRooms, Integer numberOfBeds, String titlePart, String categoryPart, LocalDateTime startDate, LocalDateTime finishDate) {
        Specification<Apartment> spec = Specification.where(null);
        spec = spec.and(ApartmentsSpecifications.statusEqual());
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
           // spec = spec.and(ApartmentsSpecifications.freeBookingDates(startDate, finishDate));
        }
        return apartmentsRepository.findAll(spec);
    }

    public Optional<Apartment> findById(Long id) {
        return apartmentsRepository.findById(id);
    }

    public Apartment save(Apartment apartment) {
        apartment.setStatus(ApartmentStatus.ACTIVE);
        return apartmentsRepository.save(apartment);
    }

    public Apartment findByIdWithActiveStatus(Long id) throws ResourceNotFoundException {
        return apartmentsRepository.findWithActiveStatus(id, ApartmentStatus.ACTIVE).orElseThrow(() -> new ResourceNotFoundException(String.format("Данные апартаменты: %s недоступны для бронирования!", id)));
    }

    public List<Apartment> findActiveApartmentsByUsername(String username) {
        return apartmentsRepository.findAllByUsernameAndStatus(username, ApartmentStatus.ACTIVE);
    }

    public List<Apartment> findInactiveApartmentsByUsername(String username) {
        return apartmentsRepository.findAllByUsernameAndStatus(username, ApartmentStatus.NOT_ACTIVE);
    }

    @Transactional
    public void deactivateById(Long id, String username) throws ResourceIsForbiddenException {
        Apartment apartment = apartmentsRepository.findByIdAndUsernameAndStatus(id, username, ApartmentStatus.ACTIVE)
                .orElseThrow(() -> new ResourceIsForbiddenException(String.format("Данный апартамент не пренадлежит пользователю: %s", username)));
        apartment.setStatus(ApartmentStatus.NOT_ACTIVE);
    }

    @Transactional
    public void activateById(Long id, String username) throws ResourceIsForbiddenException {
        Apartment apartment = apartmentsRepository.findByIdAndUsernameAndStatus(id, username, ApartmentStatus.NOT_ACTIVE)
                .orElseThrow(() -> new ResourceIsForbiddenException(String.format("Данный апартамент не пренадлежит пользователю: %s", username)));
        apartment.setStatus(ApartmentStatus.ACTIVE);
    }

    @Transactional
    public void deleteById(Long id, String username) throws ResourceIsForbiddenException {
        Apartment apartment = apartmentsRepository.findByIdAndUsername(id, username)
                .orElseThrow(() -> new ResourceIsForbiddenException(String.format("Данный апартамент не пренадлежит пользователю: %s", username)));
        apartment.setStatus(ApartmentStatus.DELETED);
    }

    @Transactional
    public Apartment update(ApartmentDto apartmentDto) throws ResourceIsForbiddenException {
        Apartment apartment = apartmentsRepository.findByIdAndUsernameAndStatus(apartmentDto.getId(), apartmentDto.getUsername(), ApartmentStatus.ACTIVE)
                .orElseThrow(() -> new ResourceIsForbiddenException(String.format("Данный апартамент не пренадлежит пользователю: %s", apartmentDto.getUsername())));
        if (apartmentDto.getTitle() != null) {
            apartment.setTitle(apartmentDto.getTitle());
        }
        if (apartmentDto.getCategory() != null) {
            apartment.getApartmentCategory().setTitle(apartmentDto.getCategory());
        }
        if (apartmentDto.getAddressDto().getCity() != null) {
            apartment.getAddress().setCity(apartmentDto.getAddressDto().getCity());
        }
        if (apartmentDto.getAddressDto().getStreet() != null) {
            apartment.getAddress().setStreet(apartmentDto.getAddressDto().getStreet());
        }
        if (apartmentDto.getAddressDto().getBuildingNumber() != null) {
            apartment.getAddress().setBuildingNumber(apartmentDto.getAddressDto().getBuildingNumber());
        }
        if (apartmentDto.getAddressDto().getBuildingNumber() != null) {
            apartment.getAddress().setBuildingNumber(apartmentDto.getAddressDto().getBuildingNumber());
        }
        if (apartmentDto.getSquareMeters() != null) {
            apartment.setSquareMeters(apartmentDto.getSquareMeters());
        }
        if (apartmentDto.getNumberOfGuests() != null) {
            apartment.setNumberOfGuests(apartmentDto.getNumberOfGuests());
        }
        if (apartmentDto.getNumberOfRooms() != null) {
            apartment.setNumberOfRooms(apartmentDto.getNumberOfRooms());
        }
        if (apartmentDto.getNumberOfBeds() != null) {
            apartment.setNumberOfBeds(apartmentDto.getNumberOfBeds());
        }
        if (apartmentDto.getPricePerNight() != null) {
            apartment.setPricePerNight(apartmentDto.getPricePerNight());
        }
        return apartment;
    }
}
