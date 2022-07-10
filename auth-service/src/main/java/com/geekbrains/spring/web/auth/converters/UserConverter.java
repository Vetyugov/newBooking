package com.geekbrains.spring.web.auth.converters;

import com.geekbrains.spring.web.api.core.ProfileDto;
import com.geekbrains.spring.web.api.dto.LegalHostDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.auth.entities.GrantedAuthority;
import com.geekbrains.spring.web.auth.entities.Host;
import com.geekbrains.spring.web.auth.entities.User;
import com.geekbrains.spring.web.auth.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public User profileDtoToUserConverter(ProfileDto profileDto) {
        return new User(profileDto.getId(), profileDto.getUsername(),
                profileDto.getPassword(), profileDto.getEmail(),
                profileDto.getName(), profileDto.getPatronymic(),
                profileDto.getSurname());
    }

    public ProfileDto userToProfileDtoConverter(User user) {
        return new ProfileDto(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(),
                user.getName(), user.getPatronymic(), user.getSurname());
    }
}
