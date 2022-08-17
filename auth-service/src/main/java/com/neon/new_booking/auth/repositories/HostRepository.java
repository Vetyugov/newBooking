package com.neon.new_booking.auth.repositories;

import com.neon.new_booking.auth.entities.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {
    Optional<Host> findByUsername(String username);

    Boolean existsByUsername(String username);
}