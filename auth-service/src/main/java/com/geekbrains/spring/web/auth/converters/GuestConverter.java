package com.geekbrains.spring.web.auth.converters;

import com.geekbrains.spring.web.api.dto.GuestDto;
import com.geekbrains.spring.web.auth.entities.Guest;
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
        throw new UnsupportedOperationException();
    }

//    public Guest guestDtoToEntity(GuestDto guestDto) {
//        return new Guest(guestDto.setUserId(), guestDto.getName(),
//                guestDto.getPatronymic(), guestDto.getSurname(),
//                guestDto.getEmail(), guestDto.getUsername(),
//                guestDto.getPassword());
//    }

    public GuestDto entityToGuestDto(Guest guest) {
        return new GuestDto(guest.getUser().getId(), guest.getName(),
                guest.getPatronymic(), guest.getSurname(),
                guest.getEmail(), guest.getUsername(),
                guest.getPassword());
    }
}