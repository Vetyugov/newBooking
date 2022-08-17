package com.neon.new_booking.core.repositories;

import com.neon.new_booking.core.entities.BookingDate;
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

    Optional<BookingDate> findBookingDateByApartmentIdAndStartDateAndFinishDate(@Param("apartmentId") Long apartmentId, @Param("start") LocalDate start, @Param("finish") LocalDate finish);
}
