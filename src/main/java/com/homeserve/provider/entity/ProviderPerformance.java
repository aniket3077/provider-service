package com.homeserve.provider.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "provider_performance",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_provider_performance_provider",
                        columnNames = "provider_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "provider_id",
            nullable = false,
            unique = true
    )
    private Provider provider;

    @Column(name = "total_offers", nullable = false)
    private Integer totalOffers = 0;

    @Column(name = "accepted_offers", nullable = false)
    private Integer acceptedOffers = 0;

    @Column(name = "rejected_offers", nullable = false)
    private Integer rejectedOffers = 0;

    @Column(name = "expired_offers", nullable = false)
    private Integer expiredOffers = 0;

    @Column(name = "total_jobs", nullable = false)
    private Integer totalJobs = 0;

    @Column(name = "completed_jobs", nullable = false)
    private Integer completedJobs = 0;

    @Column(name = "cancelled_jobs", nullable = false)
    private Integer cancelledJobs = 0;

    @Column(name = "average_response_time_seconds")
    private Double averageResponseTimeSeconds = 0.0;

    @Column(name = "active_jobs", nullable = false)
    private Integer activeJobs = 0;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = LocalDateTime.now();

        if (totalOffers == null) totalOffers = 0;
        if (acceptedOffers == null) acceptedOffers = 0;
        if (rejectedOffers == null) rejectedOffers = 0;
        if (expiredOffers == null) expiredOffers = 0;
        if (totalJobs == null) totalJobs = 0;
        if (completedJobs == null) completedJobs = 0;
        if (cancelledJobs == null) cancelledJobs = 0;
        if (activeJobs == null) activeJobs = 0;
        if (averageResponseTimeSeconds == null) {
            averageResponseTimeSeconds = 0.0;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}