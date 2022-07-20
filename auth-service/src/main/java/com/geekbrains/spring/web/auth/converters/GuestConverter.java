package com.geekbrains.spring.web.auth.converters;

import com.geekbrains.spring.web.api.core.ProfileDto;
import com.geekbrains.spring.web.api.dto.GuestDto;
import com.geekbrains.spring.web.auth.entities.Guest;
import com.geekbrains.spring.web.auth.entities.Host;
import com.geekbrains.spring.web.auth.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GuestConverter {
    /*
    конвертер для гостя (клиент, постоялец)
     */

    public GuestDto entityToGuestDto(Guest guest) {
        return new GuestDto(guest.getId(), guest.getUser().getId(), guest.getName(),
                guest.getPatronymic(), guest.getSurname(), guest.getUsername());
    }

    public Guest guestDtoToEntity(ProfileDto profileDto, User user) {
        return Guest.builder()
                .username(profileDto.getUsername())
                .user(user)
                .build();
    }
}