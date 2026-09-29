package com.homeserve.provider.repository;

import com.homeserve.provider.entity.ProviderRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProviderRatingRepository
        extends JpaRepository<ProviderRating, Long> {

    Optional<ProviderRating> findByProviderIdAndBookingId(
            Long providerId,
            Long bookingId
    );

    boolean existsByProviderIdAndBookingId(
            Long providerId,
            Long bookingId
    );

    List<ProviderRating> findByProviderIdOrderByCreatedAtDesc(
            Long providerId
    );

    long countByProviderId(Long providerId);

    @Query("""
        SELECT AVG(r.rating)
        FROM ProviderRating r
        WHERE r.provider.id = :providerId
    """)
    BigDecimal findAverageRating(
            @Param("providerId") Long providerId
    );
}