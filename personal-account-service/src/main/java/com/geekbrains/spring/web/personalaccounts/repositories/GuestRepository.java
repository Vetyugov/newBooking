package com.geekbrains.spring.web.personalaccounts.repositories;

import com.geekbrains.spring.web.personalaccounts.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByUsername(String username);
}
