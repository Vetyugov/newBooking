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
конвертер для частного владельца
 */
    public Host individualHostDtoToEntity(IndividualHostDto individualHostDto) {
        throw new UnsupportedOperationException();
    }

    public IndividualHostDto entityToIndividualHostDto(Host host) {
        return new IndividualHostDto(host.getId(), host.getRole().getId(), host.getUser().getId(), host.getName(),
                host.getPatronymic(), host.getSurname(),
                host.getEmail(), host.getUsername(),
                host.getPassword(), host.getPostcode(),
                host.getCountry(), host.getAddress(),
                host.getInn(), host.getAccount());
    }

    /*
    конвертер для юрлица
     */
    public Host legalHostDtoToEntity(LegalHostDto legalHostDto) {
        throw new UnsupportedOperationException();
    }

    public LegalHostDto entityToLegalHostDto(Host host) {
        return new LegalHostDto(host.getId(), host.getRole().getId(), host.getUser().getId(), host.getName(),
                host.getPatronymic(), host.getSurname(),
                host.getEmail(), host.getUsername(),
                host.getPassword(), host.getTitleFirm(),
                host.getCountry(), host.getOfficeAddress(),
                host.getPostcode(), host.getInn(), host.getAccount());
    }
}