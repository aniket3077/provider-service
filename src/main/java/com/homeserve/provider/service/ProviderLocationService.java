package com.homeserve.provider.service;

import com.homeserve.provider.dto.location.ProviderLocationRequest;
import com.homeserve.provider.dto.location.ProviderLocationResponse;
import com.homeserve.provider.entity.Provider;
import com.homeserve.provider.entity.ProviderLocation;
import com.homeserve.provider.repository.ProviderLocationRepository;
import com.homeserve.provider.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProviderLocationService {

    private final ProviderLocationRepository locationRepository;
    private final CurrentProviderService currentProviderService;

    @Transactional
    public ProviderLocationResponse updateMyLocation(
            ProviderLocationRequest request) {

        Provider provider = currentProviderService.getCurrentProvider();

        ProviderLocation location =
                locationRepository.findByProviderId(provider.getId())
                        .orElse(
                                ProviderLocation.builder()
                                        .provider(provider)
                                        .build()
                        );

        location.setLatitude(request.getLatitude());
        location.setLongitude(request.getLongitude());

        ProviderLocation saved =
                locationRepository.save(location);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public ProviderLocationResponse getMyLocation() {

        Provider provider = currentProviderService.getCurrentProvider();

        ProviderLocation location =
                locationRepository.findByProviderId(provider.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Provider location not found"
                                ));

        return mapToResponse(location);
    }

    @Transactional
    public void deleteMyLocation() {

        Provider provider = currentProviderService.getCurrentProvider();

        ProviderLocation location =
                locationRepository.findByProviderId(provider.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Provider location not found"
                                ));

        locationRepository.delete(location);
    }

    private ProviderLocationResponse mapToResponse(
            ProviderLocation location) {

        return ProviderLocationResponse.builder()
                .providerId(location.getProvider().getId())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .updatedAt(location.getUpdatedAt())
                .build();
    }
}