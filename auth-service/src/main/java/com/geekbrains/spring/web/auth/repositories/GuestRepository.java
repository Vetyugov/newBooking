package com.geekbrains.spring.web.auth.repositories;

import com.geekbrains.spring.web.api.core.ProfileDto;
import com.geekbrains.spring.web.auth.entities.Guest;
import com.geekbrains.spring.web.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByUsername(String username);
}
