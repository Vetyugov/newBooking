package com.geekbrains.spring.web.core.repositories;

import com.geekbrains.spring.web.core.entities.Apartment;
import com.geekbrains.spring.web.core.entities.BookingDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApartmentsRepository extends JpaRepository<Apartment, Long>, JpaSpecificationExecutor<Apartment> {
    @Query("select a from Apartment a where not exists " +
            "(select d from BookingDate d where a.id = d.apartment.id and (d.startDate < :finish and d.finishDate > :start))")
    List<Apartment> findFilteredApartments(@Param("start") LocalDate start, @Param("finish") LocalDate finish);

   // @Query("select a from Apartment a where a.username = ?1")
    List<Apartment> findAllByUsername(String username);
}
