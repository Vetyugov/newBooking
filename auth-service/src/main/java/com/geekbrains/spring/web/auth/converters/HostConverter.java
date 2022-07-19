package com.geekbrains.spring.web.auth.converters;

import com.geekbrains.spring.web.api.core.ProfileDto;
import com.geekbrains.spring.web.api.dto.IndividualHostDto;
import com.geekbrains.spring.web.api.dto.LegalHostDto;
import com.geekbrains.spring.web.auth.entities.Host;
import com.geekbrains.spring.web.auth.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HostConverter {

    /*
конвертер для частного владельца
 */
//    public Host individualHostDtoToEntity(IndividualHostDto individualHostDto) {
//        throw new UnsupportedOperationException();
//    }

    public IndividualHostDto entityToIndividualHostDto(Host host) {
        return new IndividualHostDto(host.getId(), host.getUser().getId(), host.getName(),
                host.getPatronymic(), host.getSurname(), host.getUsername(),
                host.getPostcode(), host.getCountry(), host.getAddress(),
                host.getInn(), host.getAccount());
    }

    /*
    конвертер для юрлица
     */
//    public Host legalHostDtoToEntity(LegalHostDto legalHostDto) {
//        throw new UnsupportedOperationException();
//    }

    public LegalHostDto entityToLegalHostDto(Host host) {
        return new LegalHostDto(host.getId(), host.getUser().getId(), host.getName(),
                host.getPatronymic(), host.getSurname(),
                host.getUsername(), host.getTitleFirm(),
                host.getCountry(), host.getOfficeAddress(),
                host.getPostcode(), host.getInn(), host.getAccount());
    }

    public Host legalHostDtoToEntity(ProfileDto profileDto, User user) {
        return Host.builder()
                .username(profileDto.getUsername())
                .user(user)
                .build();
    }
}