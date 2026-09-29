package com.homeserve.provider.service;

import com.homeserve.provider.dto.provider.ProviderServiceMappingRequest;
import com.homeserve.provider.dto.provider.ProviderServiceMappingResponse;
import com.homeserve.provider.entity.Provider;
import com.homeserve.provider.entity.ProviderServiceMapping;
import com.homeserve.provider.repository.ProviderRepository;
import com.homeserve.provider.repository.ProviderServiceMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderServiceMappingService {

    private final ProviderServiceMappingRepository mappingRepository;
    private final ProviderRepository providerRepository;
    private final CurrentProviderService currentProviderService;

    // Provider adds a service to their own profile
    @Transactional
    public ProviderServiceMappingResponse addMyService(
            ProviderServiceMappingRequest request) {

        Provider provider = currentProviderService.getCurrentProvider();

        if (mappingRepository.existsByProviderIdAndServiceId(
                provider.getId(),
                request.getServiceId())) {

            throw new RuntimeException(
                    "Service is already mapped to this provider");
        }

        ProviderServiceMapping mapping = ProviderServiceMapping.builder()
                .provider(provider)
                .serviceId(request.getServiceId())
                .active(true)
                .build();

        ProviderServiceMapping saved =
                mappingRepository.save(mapping);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ProviderServiceMappingResponse> getMyServices() {

        Provider provider = currentProviderService.getCurrentProvider();

        return mappingRepository
                .findByProviderId(provider.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public ProviderServiceMappingResponse activateMyService(
            Long serviceId) {

        Provider provider = currentProviderService.getCurrentProvider();

        ProviderServiceMapping mapping =
                mappingRepository
                        .findByProviderIdAndServiceId(
                                provider.getId(),
                                serviceId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Service mapping not found"));

        mapping.setActive(true);

        return mapToResponse(
                mappingRepository.save(mapping)
        );
    }

    @Transactional
    public ProviderServiceMappingResponse deactivateMyService(
            Long serviceId) {

        Provider provider = currentProviderService.getCurrentProvider();

        ProviderServiceMapping mapping =
                mappingRepository
                        .findByProviderIdAndServiceId(
                                provider.getId(),
                                serviceId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Service mapping not found"));

        mapping.setActive(false);

        return mapToResponse(
                mappingRepository.save(mapping)
        );
    }

    @Transactional
    public void deleteMyService(Long serviceId) {

        Provider provider = currentProviderService.getCurrentProvider();

        ProviderServiceMapping mapping =
                mappingRepository
                        .findByProviderIdAndServiceId(
                                provider.getId(),
                                serviceId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Service mapping not found"));

        mappingRepository.delete(mapping);
    }

    private ProviderServiceMappingResponse mapToResponse(
            ProviderServiceMapping mapping) {

        return ProviderServiceMappingResponse.builder()
                .id(mapping.getId())
                .providerId(mapping.getProvider().getId())
                .serviceId(mapping.getServiceId())
                .active(mapping.getActive())
                .createdAt(mapping.getCreatedAt())
                .updatedAt(mapping.getUpdatedAt())
                .build();
    }
}