package com.homeserve.provider.repository;

import com.homeserve.provider.entity.ProviderServiceMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProviderServiceMappingRepository
        extends JpaRepository<ProviderServiceMapping, Long> {

    List<ProviderServiceMapping> findByProviderId(
            Long providerId
    );

    List<ProviderServiceMapping> findByProviderIdAndActive(
            Long providerId,
            Boolean active
    );

    List<ProviderServiceMapping> findByServiceIdAndActive(
            Long serviceId,
            Boolean active
    );

    Optional<ProviderServiceMapping> findByProviderIdAndServiceId(
            Long providerId,
            Long serviceId
    );

    boolean existsByProviderIdAndServiceId(
            Long providerId,
            Long serviceId
    );

    boolean existsByProviderIdAndServiceIdAndActive(
            Long providerId,
            Long serviceId,
            Boolean active
    );

    void deleteByProviderIdAndServiceId(
            Long providerId,
            Long serviceId
    );
}