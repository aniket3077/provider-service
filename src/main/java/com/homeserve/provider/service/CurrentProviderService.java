package com.homeserve.provider.service;

import com.homeserve.provider.entity.Provider;
import com.homeserve.provider.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentProviderService {

    private final ProviderRepository providerRepository;

    public Provider getCurrentProvider() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {

            throw new RuntimeException("Provider is not authenticated");
        }

        String email = authentication.getName();

        return providerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Provider not found with email: " + email
                        ));
    }
}