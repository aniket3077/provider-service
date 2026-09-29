package com.homeserve.provider.repository;

import com.homeserve.provider.entity.AvailabilityStatus;
import com.homeserve.provider.entity.ProviderAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ProviderAvailabilityRepository
        extends JpaRepository<ProviderAvailability, Long> {

    List<ProviderAvailability> findByProviderId(
            Long providerId
    );

    List<ProviderAvailability> findByProviderIdAndAvailableDate(
            Long providerId,
            LocalDate availableDate
    );

    List<ProviderAvailability> findByProviderIdAndAvailableDateAndStatus(
            Long providerId,
            LocalDate availableDate,
            AvailabilityStatus status
    );

    @Query("""
            SELECT a
            FROM ProviderAvailability a
            WHERE a.provider.id = :providerId
              AND a.availableDate = :availableDate
              AND a.status = :status
              AND a.startTime < :requestedEndTime
              AND a.endTime > :requestedStartTime
            """)
    List<ProviderAvailability> findOverlappingAvailability(
            @Param("providerId") Long providerId,
            @Param("availableDate") LocalDate availableDate,
            @Param("requestedStartTime") LocalTime requestedStartTime,
            @Param("requestedEndTime") LocalTime requestedEndTime,
            @Param("status") AvailabilityStatus status
    );

    void deleteByIdAndProviderId(Long id, Long providerId);
}