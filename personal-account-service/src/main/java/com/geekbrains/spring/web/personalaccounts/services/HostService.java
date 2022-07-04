package com.geekbrains.spring.web.personalaccounts.services;

import com.geekbrains.spring.web.auth.entities.Role;
import com.geekbrains.spring.web.auth.repositories.UserRepository;
import com.geekbrains.spring.web.personalaccounts.converters.HostConverter;
import com.geekbrains.spring.web.personalaccounts.dto.IndividualHostDto;
import com.geekbrains.spring.web.personalaccounts.dto.LegalHostDto;
import com.geekbrains.spring.web.personalaccounts.entities.Host;
import com.geekbrains.spring.web.personalaccounts.repositories.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HostService {
    private final HostRepository hostRepository;

    public Optional<Host> findById(Long id) {
        return hostRepository.findById(id);
    }

    public Host findByUsername(String username) {
        return hostRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Host %s not found.", username)));
    }

    public Boolean existByUsername(String username) {
        return hostRepository.existsByUsername(username);
    }

    public Boolean existByEmail(String email) {
        return hostRepository.existsByEmail(email);
    }

    public Boolean emailBelongsToLegalHost (LegalHostDto legalHostDto){
        return findByUsername(legalHostDto.getUsername()).getEmail().equals(legalHostDto.getEmail());
    }

    public Boolean emailBelongsToIndividualHost (IndividualHostDto individualHostDto){
        return findByUsername(individualHostDto.getUsername()).getEmail().equals(individualHostDto.getEmail());
    }

}
