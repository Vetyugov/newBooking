package com.geekbrains.spring.web.auth.services;

import com.geekbrains.spring.web.api.core.UserDto;
import com.geekbrains.spring.web.api.exceptions.AppError;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.auth.converters.UserConverter;
import com.geekbrains.spring.web.auth.entities.Role;
import com.geekbrains.spring.web.auth.entities.RoleName;
import com.geekbrains.spring.web.auth.entities.User;
import com.geekbrains.spring.web.auth.repositories.RoleRepository;
import com.geekbrains.spring.web.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserConverter userConverter;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final HostService hostService;
    private final GuestService guestService;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        ArrayList<Role> userArrayRole = new ArrayList();
        userArrayRole.add(user.getRole());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(userArrayRole));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
    }

    @Transactional
    public void createOrUpdateUser(UserDto userDto)  {
        if (userDto.getId() != null) {
            User user = userRepository.getById(userDto.getId());
            user.setEmail(userDto.getEmail());
            user.setRole(roleRepository.findByName(RoleName.valueOf(userDto.getRoleName())).orElseThrow(()-> new ResourceNotFoundException("Error такой роли не существует")));
            userRepository.save(user);
            return;
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRole(roleRepository.findByName(RoleName.valueOf(userDto.getRoleName())).orElseThrow(()-> new ResourceNotFoundException("Error такой роли не существует")));
        userRepository.save(user);
        if (RoleName.valueOf(userDto.getRoleName()).equals(RoleName.ROLE_LEGAL_HOST) || RoleName.valueOf(userDto.getRoleName()).equals(RoleName.ROLE_INDIVIDUAL_HOST)) {
            hostService.createNewHost(userDto, user);
        }
        if (RoleName.valueOf(userDto.getRoleName()).equals(RoleName.ROLE_GUEST)) {
            guestService.createNewGuest(userDto, user);
        }
        return;
    }

    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

//    public boolean emailBelongsToThisUser(UserDto userDto) {
//        return findByUsername(userDto.getUsername()).getEmail().equals(userDto.getEmail());
//    }
}