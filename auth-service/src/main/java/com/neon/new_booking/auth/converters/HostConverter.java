package com.neon.new_booking.auth.converters;

import com.neon.new_booking.api.core.UserDto;
import com.neon.new_booking.api.dto.IndividualHostDto;
import com.neon.new_booking.api.dto.LegalHostDto;
import com.neon.new_booking.auth.entities.Host;
import com.neon.new_booking.auth.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HostConverter {

    /*
конвертер для частного владельца
 */

    public IndividualHostDto entityToIndividualHostDto(Host host) {
        return new IndividualHostDto(host.getId(), host.getUser().getId(), host.getName(),
                host.getPatronymic(), host.getSurname(), host.getUsername(),
                host.getCountry(),  host.getAddress(), host.getPostcode(),
                host.getInn(), host.getAccount());
    }
    /*
    конвертер для юрлица
     */

    public LegalHostDto entityToLegalHostDto(Host host) {
        return new LegalHostDto(host.getId(), host.getUser().getId(), host.getName(),
                host.getPatronymic(), host.getSurname(),
                host.getUsername(), host.getTitleFirm(),
                host.getCountry(), host.getOfficeAddress(),
                host.getPostcode(), host.getInn(), host.getAccount());
    }

    /*
    конвертер для всех хостов
    */
    public Host hostDtoToEntity(UserDto userDto, User user) {
        return Host.builder()
                .username(userDto.getUsername())
                .user(user)
                .build();
    }
}