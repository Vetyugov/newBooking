package com.geekbrains.spring.web.auth.services;

import com.geekbrains.spring.web.auth.entities.Guest;
import com.geekbrains.spring.web.auth.repositories.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;

    public Optional<Guest> findById(Long id) { return guestRepository.findById(id);
    }

    public Guest findByUsername(String username) {
        return guestRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Guest %s not found.", username)));
    }
}