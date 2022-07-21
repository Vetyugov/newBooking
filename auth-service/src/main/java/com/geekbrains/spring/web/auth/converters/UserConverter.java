package com.geekbrains.spring.web.auth.converters;

import com.geekbrains.spring.web.api.core.UserDto;
import com.geekbrains.spring.web.auth.entities.User;
import com.geekbrains.spring.web.auth.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final RoleRepository roleRepository;

    public UserDto userToUserDtoConverter(User user) {
        return new UserDto(user.getId(), user.getRole().getName(), user.getUsername(), user.getPassword(), user.getEmail());
    }
}
