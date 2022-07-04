package com.geekbrains.spring.web.personalaccounts.converters;

import com.geekbrains.spring.web.personalaccounts.dto.GuestDto;
import com.geekbrains.spring.web.personalaccounts.entities.Guest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GuestConverter {
    /*
    конвертер для гостя (клиент, постоялец)
     */
    public Guest guestDtoToEntity(GuestDto guestDto) {
        return new Guest(guestDto.getId(), guestDto.getName(),
                guestDto.getPatronymic(), guestDto.getSurname(),
                guestDto.getEmail(), guestDto.getUsername(),
                guestDto.getPassword());
    }

    public GuestDto entityToGuestDto(Guest guest) {
        return new GuestDto(guest.getId(), guest.getName(),
                guest.getPatronymic(), guest.getSurname(),
                guest.getEmail(), guest.getUsername(),
                guest.getPassword());
    }
}
