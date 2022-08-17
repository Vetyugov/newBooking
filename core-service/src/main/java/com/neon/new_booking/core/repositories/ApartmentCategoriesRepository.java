package com.neon.new_booking.core.repositories;
import com.neon.new_booking.core.entities.ApartmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApartmentCategoriesRepository extends JpaRepository<ApartmentCategory, Long>, JpaSpecificationExecutor<ApartmentCategory> {
    @Query("select c from ApartmentCategory c where c.title = ?1")
    Optional<ApartmentCategory> findByTitle(String title);
}
