package com.homeserve.provider.repository;

import com.homeserve.provider.entity.ProviderPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderPerformanceRepository
        extends JpaRepository<ProviderPerformance, Long> {

    Optional<ProviderPerformance> findByProviderId(
            Long providerId
    );
}