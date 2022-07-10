package com.geekbrains.spring.web.auth.converters;

import com.geekbrains.spring.web.api.dto.IndividualHostDto;
import com.geekbrains.spring.web.api.dto.LegalHostDto;
import com.geekbrains.spring.web.auth.entities.Host;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HostConverter {
    /*
    конвертер для юрлица
     */
    public Host legalHostDtoToEntity(LegalHostDto legalHostDto) {
        return new Host(legalHostDto.getId(), legalHostDto.getName(),
                legalHostDto.getPatronymic(), legalHostDto.getSurname(),
                legalHostDto.getEmail(), legalHostDto.getUsername(),
                legalHostDto.getPassword(), legalHostDto.getTitleFirm(),
                legalHostDto.getCountry(), legalHostDto.getOfficeAddress(),
                legalHostDto.getPostcode(), legalHostDto.getInn(), legalHostDto.getAccount());
    }

    public LegalHostDto entityToLegalHostDto(Host host) {
        return new LegalHostDto(host.getId(), host.getName(),
                host.getPatronymic(), host.getSurname(),
                host.getEmail(), host.getUsername(),
                host.getPassword(), host.getTitleFirm(),
                host.getCountry(), host.getOfficeAddress(),
                host.getPostcode(), host.getInn(), host.getAccount());
    }
    /*
    конвертер для частного владельца
     */
    public Host individualHostDtoToEntity(IndividualHostDto individualHostDto) {
        return new Host(individualHostDto.getId(), individualHostDto.getName(),
                individualHostDto.getPatronymic(), individualHostDto.getSurname(),
                individualHostDto.getEmail(), individualHostDto.getUsername(),
                individualHostDto.getPassword(), individualHostDto.getCountry(),
                individualHostDto.getAddress(), individualHostDto.getPostcode(),
                individualHostDto.getInn(), individualHostDto.getAccount());
    }

    public IndividualHostDto entityToIndividualHostDto(Host host) {
        return new IndividualHostDto(host.getId(), host.getName(),
                host.getPatronymic(), host.getSurname(),
                host.getEmail(), host.getUsername(),
                host.getPassword(), host.getPostcode(), host.getCountry(),
                host.getAddress(),
                host.getInn(), host.getAccount());
    }
}