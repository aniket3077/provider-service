package com.homeserve.provider.repository;

import com.homeserve.provider.entity.ProviderLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderLocationRepository
        extends JpaRepository<ProviderLocation, Long> {

    Optional<ProviderLocation> findByProviderId(Long providerId);

    boolean existsByProviderId(Long providerId);

    void deleteByProviderId(Long providerId);
}