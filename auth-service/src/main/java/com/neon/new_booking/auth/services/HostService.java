package com.neon.new_booking.auth.services;

import com.neon.new_booking.api.core.UserDto;
import com.neon.new_booking.api.dto.IndividualHostDto;
import com.neon.new_booking.api.dto.LegalHostDto;
import com.neon.new_booking.auth.converters.HostConverter;
import com.neon.new_booking.auth.entities.Host;
import com.neon.new_booking.auth.entities.User;
import com.neon.new_booking.auth.repositories.HostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class  HostService {
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
            if (legalHostDto.getSurname() != null) {
                host.setSurname(legalHostDto.getSurname());
            }
            if (legalHostDto.getName() != null) {
                host.setName(legalHostDto.getName());
            }
            if (legalHostDto.getPatronymic() != null) {
                host.setPatronymic(legalHostDto.getPatronymic());
            }
            if (legalHostDto.getTitleFirm() != null) {
                host.setTitleFirm(legalHostDto.getTitleFirm());
            }
            if (legalHostDto.getInn() != null) {
                host.setInn(legalHostDto.getInn());
            }
            if (legalHostDto.getCountry() != null) {
                host.setCountry(legalHostDto.getCountry());
            }
            if (legalHostDto.getOfficeAddress() != null) {
                host.setOfficeAddress(legalHostDto.getOfficeAddress());
            }
            if (legalHostDto.getPostcode() != null) {
                host.setPostcode(legalHostDto.getPostcode());
            }
            if (legalHostDto.getAccount() != null) {
                host.setAccount(legalHostDto.getAccount());
            }
        }
    }

    @Transactional
    public void updateIndividualHost(IndividualHostDto individualHostDto) {
        if (individualHostDto.getId() != null && hostRepository.existsById(individualHostDto.getId())) {
            log.info("нашел " + individualHostDto.getId());
            Host host = hostRepository.getById(individualHostDto.getId());
            if (individualHostDto.getSurname() != null) {
                host.setSurname(individualHostDto.getSurname());
            }
            if (individualHostDto.getName() != null) {
                host.setName(individualHostDto.getName());
            }
            if (individualHostDto.getPatronymic() != null) {
                host.setPatronymic(individualHostDto.getPatronymic());
            }
            if (individualHostDto.getCountry() != null) {
                host.setCountry(individualHostDto.getCountry());
            }
            if (individualHostDto.getAddress() != null) {
                host.setAddress(individualHostDto.getAddress());
            }
            if (individualHostDto.getPostcode() != null) {
                host.setPostcode(individualHostDto.getPostcode());
            }
            if (individualHostDto.getInn() != null) {
                host.setInn(individualHostDto.getInn());
            }
            if (individualHostDto.getAccount() != null) {
                host.setAccount(individualHostDto.getAccount());
            }
        }
    }
}