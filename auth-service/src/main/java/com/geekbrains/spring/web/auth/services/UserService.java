package com.geekbrains.spring.web.auth.services;

import com.geekbrains.spring.web.api.core.ProfileDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.web.auth.converters.UserConverter;
import com.geekbrains.spring.web.auth.entities.Role;
import com.geekbrains.spring.web.auth.entities.User;
import com.geekbrains.spring.web.auth.repositories.RoleRepository;
import com.geekbrains.spring.web.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
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
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Transactional
    public void saveUser(ProfileDto profileDto) {
        if (profileDto.getId() != null && userRepository.existsById(profileDto.getId())) {
            User user = userRepository.getById(profileDto.getId());
            user.setEmail(profileDto.getEmail());
            user.setRole(roleRepository.findByName(profileDto.getRoleName()).get());
            userRepository.save(user);
            return;
        }
        User user = new User();
        user.setUsername(profileDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(profileDto.getPassword()));
        user.setEmail(profileDto.getEmail());
        user.setRole(roleRepository.findByName(profileDto.getRoleName()).get());
        userRepository.save(user);
        if (profileDto.getRoleName().equals("ROLE_LEGAL_HOST") || profileDto.getRoleName().equals("ROLE_INDIVIDUAL_HOST")) {
            hostService.createNewHost(profileDto, user);
        }
        if (profileDto.getRoleName().equals("ROLE_GUEST_HOST")) {
            guestService.createNewGuest(profileDto, user);
        }
        return;
    }

    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

//    public boolean emailBelongsToThisUser(ProfileDto profileDto) {
//        return findByUsername(profileDto.getUsername()).getEmail().equals(profileDto.getEmail());
//    }
}