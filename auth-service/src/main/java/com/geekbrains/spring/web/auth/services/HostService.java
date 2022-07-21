package com.geekbrains.spring.web.auth.services;

import com.geekbrains.spring.web.api.core.UserDto;
import com.geekbrains.spring.web.api.dto.IndividualHostDto;
import com.geekbrains.spring.web.api.dto.LegalHostDto;
import com.geekbrains.spring.web.auth.converters.HostConverter;
import com.geekbrains.spring.web.auth.entities.Host;
import com.geekbrains.spring.web.auth.entities.User;
import com.geekbrains.spring.web.auth.repositories.HostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    @Transactional
    public void createNewHost(UserDto userDto, User user) {
        Host host = hostConverter.hostDtoToEntity(userDto, user);
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