package com.homeserve.provider.repository;

import com.homeserve.provider.entity.Provider;
import com.homeserve.provider.entity.ProviderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProviderMatchingRepository
        extends JpaRepository<Provider, Long> {

    @Query("""
        SELECT DISTINCT p
        FROM Provider p
        JOIN ProviderServiceMapping ps
            ON ps.provider.id = p.id
        JOIN ProviderLocation pl
            ON pl.provider.id = p.id
        JOIN ProviderAvailability pa
            ON pa.provider.id = p.id
        WHERE p.status = :status
          AND p.verified = :verified
          AND ps.serviceId = :serviceId
          AND ps.active = true
          AND pa.availableDate = :requestedDate
          AND pa.status = com.homeserve.provider.entity.AvailabilityStatus.AVAILABLE
          AND pa.startTime < :requestedEndTime
          AND pa.endTime > :requestedStartTime
    """)
    List<Provider> findEligibleProviders(
            @Param("serviceId") Long serviceId,
            @Param("requestedDate") java.time.LocalDate requestedDate,
            @Param("requestedStartTime") java.time.LocalTime requestedStartTime,
            @Param("requestedEndTime") java.time.LocalTime requestedEndTime,
            @Param("status") ProviderStatus status,
            @Param("verified") Boolean verified
    );
}