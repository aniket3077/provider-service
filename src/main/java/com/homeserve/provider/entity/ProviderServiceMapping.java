package com.homeserve.provider.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "provider_services",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_provider_service",
                        columnNames = {"provider_id", "service_id"}
                )
        },
        indexes = {
                @Index(
                        name = "idx_provider_service",
                        columnList = "provider_id, service_id"
                ),
                @Index(
                        name = "idx_service_active",
                        columnList = "service_id, active"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderServiceMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Provider who can perform the service.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "provider_id",
            nullable = false
    )
    private Provider provider;

    /**
     * ID of the service from the Service Catalog.
     *
     * This is NOT a foreign key because the service
     * belongs to another microservice/database.
     */
    @Column(
            name = "service_id",
            nullable = false
    )
    private Long serviceId;

    /**
     * Whether this provider-service mapping is active.
     */
    @Column(
            nullable = false
    )
    @Builder.Default
    private Boolean active = true;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "updated_at",
            nullable = false
    )
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {

        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        if (active == null) {
            active = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}