package com.geekbrains.spring.web.core.services;

import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.core.entities.ApartmentCategory;
import com.geekbrains.spring.web.core.repositories.ApartmentCategoriesRepository;
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
