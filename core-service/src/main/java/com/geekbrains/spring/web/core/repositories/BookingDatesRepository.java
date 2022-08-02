package com.geekbrains.spring.web.core.repositories;

import com.geekbrains.spring.web.core.entities.BookingDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BookingDatesRepository extends JpaRepository<BookingDate, Long>, JpaSpecificationExecutor<BookingDate> {
    @Query("select (count(d) > 0) from BookingDate d where d.apartment.id = :apartmentId and (d.startDate < :finish and d.finishDate > :start)")
    Boolean existsBookingDates(@Param("apartmentId") Long apartmentId, @Param("start") LocalDate start, @Param("finish") LocalDate finish);

    //   @Query("select d from BookingDate d where d.apartment.id = :apartmentId and (d.startDate = :start and d.finishDate = :finish) ")
    Optional<BookingDate> findBookingDateByApartmentAndStartDateAndFinishDate(@Param("apartmentId") Long apartmentId, @Param("start") LocalDate start, @Param("finish") LocalDate finish);
}
