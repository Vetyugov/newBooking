package com.geekbrains.spring.web.core.repositories.specifications;

import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.entities.BookingDate;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDate;

public class ApartmentsSpecifications {
    public static Specification<Apartment> cityLike(String cityPart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("city"), String.format("%%%s%%", cityPart));
    }

    public static Specification<Apartment> priceGreaterOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("pricePerNight"), price);
    }

    public static Specification<Apartment> priceLessThanOrEqualsThan(Integer price) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("pricePerNight"), price);
    }

    public static Specification<Apartment> squareMetersGreaterOrEqualsThan(Integer squareMeters) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("squareMeters"), squareMeters);
    }

    public static Specification<Apartment> squareMetersLessThanOrEqualsThan(Integer squareMeters) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("squareMeters"), squareMeters);
    }

    public static Specification<Apartment> numberOfGuestsEqual(Integer numberOfGuests) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("numberOfGuests"), numberOfGuests);
    }

    public static Specification<Apartment> numbersOfRoomsEqual(Integer numbersOfRooms) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("numbersOfRooms"), numbersOfRooms);
    }

    public static Specification<Apartment> numberOfBedsEqual(Integer numberOfBeds) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("numberOfBeds"), numberOfBeds);
    }

    public static Specification<Apartment> titleLike(String titlePart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

    public static Specification<Apartment> categoryLike(String categoryPart) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("apartmentCategory").get("title"), String.format("%%%s%%", categoryPart));
    }

    public static Specification<Apartment> freeBookingDates(String start, String finish) {
        return (root, query, cb) -> {
            if (finish == null) {
                return null;
            }
            if (start == null) {
                return null;
            }
            Subquery<BookingDate> subquery = query.subquery(BookingDate.class);
            Root<BookingDate> bookingDateRoot = subquery.from(BookingDate.class);
            return cb.not(cb.exists(subquery.select(bookingDateRoot)
                    .where(cb.equal(root.get("id"), (bookingDateRoot.get("apartment").get("id"))), cb.and(cb.lessThan(bookingDateRoot.get("startDate"), LocalDate.parse(finish)),
                            cb.greaterThan(bookingDateRoot.get("finishDate"), LocalDate.parse(start))))));
        };
    }
}
