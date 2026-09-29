package com.homeserve.provider.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "provider_ratings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_provider_booking_rating",
                        columnNames = {"provider_id", "booking_id"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_provider_rating_provider",
                        columnList = "provider_id"
                ),
                @Index(
                        name = "idx_provider_rating_booking",
                        columnList = "booking_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    /*
     * bookingId belongs to Customer Service.
     * We intentionally do NOT create a foreign key
     * because Provider Service has its own database.
     */
    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    /*
     * customerId also belongs to Customer Service.
     */
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(
            nullable = false,
            precision = 2,
            scale = 1
    )
    private BigDecimal rating;

    @Column(length = 1000)
    private String review;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}