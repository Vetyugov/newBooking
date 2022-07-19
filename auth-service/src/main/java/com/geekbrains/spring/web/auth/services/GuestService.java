package com.geekbrains.spring.web.auth.services;

import com.geekbrains.spring.web.api.core.ProfileDto;
import com.geekbrains.spring.web.api.dto.GuestDto;
import com.geekbrains.spring.web.auth.converters.GuestConverter;
import com.geekbrains.spring.web.auth.entities.Guest;
import com.geekbrains.spring.web.auth.entities.Host;
import com.geekbrains.spring.web.auth.entities.User;
import com.geekbrains.spring.web.auth.repositories.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestService {
    private final GuestRepository guestRepository;
    private final GuestConverter guestConverter;

    public Optional<Guest> findById(Long id) { return guestRepository.findById(id);
    }

    public Guest findByUsername(String username) {
        return guestRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Guest %s not found.", username)));
    }

    @Transactional
    public void createNewGuest(ProfileDto profileDto, User user) {
        Guest guest = guestConverter.guestDtoToEntity(profileDto, user);
        guestRepository.save(guest);
    }

    @Transactional
    public void updateGuest(GuestDto guestDto) {
        if (guestDto.getId() != null && guestRepository.existsById(guestDto.getId())) {
            log.info("нашел " + guestDto.getId());
            Guest guest = guestRepository.getById(guestDto.getId());
            guest.setSurname(guestDto.getSurname());
            guest.setName(guestDto.getName());
            guest.setPatronymic(guestDto.getPatronymic());
            guestRepository.save(guest);
            log.info("сохранен " + guest);
            return;
        }
    }
}