package com.homeserve.provider.service;

import com.homeserve.provider.dto.ProviderProfileResponse;
import com.homeserve.provider.dto.ProviderProfileUpdateRequest;
import com.homeserve.provider.entity.Provider;
import com.homeserve.provider.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProviderProfileService {

    private final ProviderRepository providerRepository;

    private final CurrentProviderService currentProviderService;

    @Transactional(readOnly = true)
    public ProviderProfileResponse getMyProfile() {

        Provider provider = currentProviderService.getCurrentProvider();

        return mapToResponse(provider);
    }

    @Transactional(readOnly = true)
    public ProviderProfileResponse getProfile(Long providerId) {

        Provider provider = providerRepository.findById(providerId)
                .orElseThrow(() ->
                        new RuntimeException("Provider not found with id: " + providerId));

        return mapToResponse(provider);
    }

    @Transactional
    public ProviderProfileResponse updateMyProfile(
            ProviderProfileUpdateRequest request) {

        Provider provider = currentProviderService.getCurrentProvider();

        if (!provider.getPhone().equals(request.getPhone())
                && providerRepository.existsByPhone(request.getPhone())) {

            throw new RuntimeException(
                    "Provider with this phone number already exists");
        }

        provider.setFirstName(request.getFirstName().trim());
        provider.setLastName(request.getLastName().trim());
        provider.setPhone(request.getPhone().trim());
        provider.setExperienceYears(request.getExperienceYears());
        provider.setProfileImageUrl(request.getProfileImageUrl());

        Provider updatedProvider = providerRepository.save(provider);

        return mapToResponse(updatedProvider);
    }
    private ProviderProfileResponse mapToResponse(Provider provider) {

        return ProviderProfileResponse.builder()
                .providerId(provider.getId())
                .firstName(provider.getFirstName())
                .lastName(provider.getLastName())
                .email(provider.getEmail())
                .phone(provider.getPhone())
                .status(provider.getStatus())
                .verified(provider.getVerified())
                .experienceYears(provider.getExperienceYears())
                .profileImageUrl(provider.getProfileImageUrl())
                .build();
    }
}