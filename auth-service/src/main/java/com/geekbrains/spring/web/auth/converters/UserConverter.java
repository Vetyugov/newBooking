package com.geekbrains.spring.web.auth.converters;

import com.geekbrains.spring.web.api.core.UserDto;
import com.geekbrains.spring.web.auth.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    public UserDto userToUserDtoConverter(User user) {
        return new UserDto(user.getId(), user.getRole().toString(), user.getUsername(), user.getPassword(), user.getEmail());
    }
}
