package com.geekbrains.spring.web.booking.integrations;

import com.geekbrains.spring.web.api.core.ApartmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ApartmentsServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${integrations.core-service.url}")
    private String apartmentServiceUrl;

    public Optional<ApartmentDto> findById(Long id) {
        ApartmentDto apartmentDto = restTemplate.getForObject(apartmentServiceUrl + "/api/v1/apartments/" + id, ApartmentDto.class);
        return Optional.ofNullable(apartmentDto);
    }
}
