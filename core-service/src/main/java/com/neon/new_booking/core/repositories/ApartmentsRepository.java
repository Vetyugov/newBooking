package com.neon.new_booking.core.repositories;

import com.neon.new_booking.core.entities.Apartment;
import com.neon.new_booking.core.entities.ApartmentStatus;
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

    @Query("select a from Apartment a where a.username = ?1 and a.status = ?2")
    List<Apartment> findAllByUsernameAndStatus(String username, ApartmentStatus status);

    @Query("select a from Apartment a where a.id = ?1 and a.username = ?2 and a.status = ?3")
    Optional<Apartment> findByIdAndUsernameAndStatus(Long id, String username, ApartmentStatus status);

    @Query("select a from Apartment a where a.id = ?1 and a.username = ?2")
    Optional<Apartment> findByIdAndUsername(Long id, String username);

    @Query("select a from Apartment a where a.id = ?1 and a.status = ?2")
    Optional<Apartment> findWithStatus(Long id, ApartmentStatus status);

}
