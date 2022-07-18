package com.geekbrains.spring.web.auth.repositories;

import com.geekbrains.spring.web.auth.entities.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {
    Optional<Host> findByUsername(String username);

//    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);
}