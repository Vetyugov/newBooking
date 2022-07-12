package com.geekbrains.spring.web.core.repositories.specifications;

import com.geekbrains.spring.web.core.entities.Apartment;
import org.springframework.data.jpa.domain.Specification;

public class ApartmentsSpecifications {
    public static Specification<Apartment> priceGreaterOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Apartment> priceLessThanOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public static Specification<Apartment> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

    public static Specification<Apartment> categoryLike(String categoryPart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("category").get("title"), String.format("%%%s%%", categoryPart));
    }
}
