package com.neon.new_booking.core.services;

import com.neon.new_booking.api.exceptions.ResourceNotFoundException;
import com.neon.new_booking.core.entities.ApartmentCategory;
import com.neon.new_booking.core.repositories.ApartmentCategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApartmentCategoriesService {
    private final ApartmentCategoriesRepository categoriesRepository;

    public ApartmentCategory getByTitle(String title) {
        return categoriesRepository.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException("Category not found, title: " + title));
    }
}
