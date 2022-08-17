package com.neon.new_booking.auth.converters;

import com.neon.new_booking.api.core.UserDto;
import com.neon.new_booking.auth.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    public UserDto userToUserDtoConverter(User user) {
        return new UserDto(user.getId(), user.getRole().toString(), user.getUsername(), user.getPassword(), user.getEmail());
    }
}
