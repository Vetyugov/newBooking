package com.neon.new_booking.auth.services;

import com.neon.new_booking.api.core.UserDto;
import com.neon.new_booking.auth.entities.Role;
import com.neon.new_booking.auth.entities.User;
import com.neon.new_booking.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
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
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRole().toString())));
    }

    @Transactional
    public void createUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRole(Role.valueOf(userDto.getRoleName()));
        userRepository.save(user);
        if (Role.isUserHost(userDto.getRoleName())) {
            hostService.createNewHost(userDto, user);
        }
        if (Role.isUserGuest(userDto.getRoleName())) {
            guestService.createNewGuest(userDto, user);
        }
    }

    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}