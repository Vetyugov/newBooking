package com.geekbrains.spring.web.auth.converters;

import com.geekbrains.spring.web.api.core.UserDto;
import com.geekbrains.spring.web.api.dto.GuestDto;
import com.geekbrains.spring.web.auth.entities.Guest;
import com.geekbrains.spring.web.auth.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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

    public Guest guestDtoToEntity(UserDto userDto, User user) {
        return Guest.builder()
                .username(userDto.getUsername())
                .user(user)
                .build();
    }
}