package com.geekbrains.spring.web.auth.services;

import com.geekbrains.spring.web.api.core.ProfileDto;
import com.geekbrains.spring.web.api.dto.IndividualHostDto;
import com.geekbrains.spring.web.api.dto.LegalHostDto;
import com.geekbrains.spring.web.auth.controllers.HostController;
import com.geekbrains.spring.web.auth.converters.HostConverter;
import com.geekbrains.spring.web.auth.entities.Host;
import com.geekbrains.spring.web.auth.entities.Role;
import com.geekbrains.spring.web.auth.entities.User;
import com.geekbrains.spring.web.auth.repositories.HostRepository;
import com.geekbrains.spring.web.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class HostService {
    private final HostRepository hostRepository;
    private final HostConverter hostConverter;

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

    @Transactional //уточнить можно ли делать транзакцию в транзакции
    public void createNewHost(ProfileDto profileDto, User user) {
        Host host = hostConverter.hostDtoToEntity(profileDto, user);
        hostRepository.save(host);
    }

    @Transactional
    public void updateLegalHost(LegalHostDto legalHostDto) {
        if (legalHostDto.getId() != null && hostRepository.existsById(legalHostDto.getId())) {
            log.info("нашел " + legalHostDto.getId());
            Host host = hostRepository.getById(legalHostDto.getId());
            host.setSurname(legalHostDto.getSurname());
            host.setName(legalHostDto.getName());
            host.setPatronymic(legalHostDto.getPatronymic());
            host.setInn(legalHostDto.getInn());
            host.setCountry(legalHostDto.getCountry());
            host.setOfficeAddress(legalHostDto.getOfficeAddress());
            host.setPostcode(legalHostDto.getPostcode());
            host.setAccount(legalHostDto.getAccount());
            hostRepository.save(host);
            log.info("сохранен " + host );
            return;
        }
    }

    @Transactional
    public void updateIndividualHost(IndividualHostDto individualHostDto) {
        if (individualHostDto.getId() != null && hostRepository.existsById(individualHostDto.getId())) {
            log.info("нашел " + individualHostDto.getId());
            Host host = hostRepository.getById(individualHostDto.getId());
            host.setSurname(individualHostDto.getSurname());
            host.setName(individualHostDto.getName());
            host.setPatronymic(individualHostDto.getPatronymic());
            host.setCountry(individualHostDto.getCountry());
            host.setAddress(individualHostDto.getAddress());
            host.setPostcode(individualHostDto.getPostcode());
            host.setAccount(individualHostDto.getAccount());
            hostRepository.save(host);
            log.info("сохранен " + host );
            return;
        }
    }
}