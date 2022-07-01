package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.api.core.ApartmentDto;
import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.repositories.ApartmentsRepository;
import com.geekbrains.spring.web.core.repositories.specifications.ApartmentsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApartmentsService {
    private final ApartmentsRepository apartmentsRepository;

    public Page<Apartment> findAll(Integer minPrice, Integer maxPrice, String partTitle, Integer page) {
        Specification<Apartment> spec = Specification.where(null);
        if (minPrice != null) {
            spec = spec.and(ApartmentsSpecifications.priceGreaterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ApartmentsSpecifications.priceLessThanOrEqualsThan(maxPrice));
        }
        if (partTitle != null) {
            spec = spec.and(ApartmentsSpecifications.titleLike(partTitle));
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

    @Transactional
    public Apartment update(ApartmentDto apartmentDto) {
        Apartment apartment = apartmentsRepository.findById(apartmentDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Невозможно обновить продукта, не надйен в базе, id: " + apartmentDto.getId()));
        apartment.setPrice(apartmentDto.getPrice());
        apartment.setTitle(apartmentDto.getTitle());
        return apartment;
    }
}
