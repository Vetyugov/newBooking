package com.geekbrains.spring.web.core.repositories;

import com.geekbrains.spring.web.core.entities.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentsRepository extends JpaRepository<Apartment, Long>, JpaSpecificationExecutor<Apartment> {
}
