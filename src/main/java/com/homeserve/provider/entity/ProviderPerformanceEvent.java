package com.homeserve.provider.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "provider_performance_events",
        indexes = {
                @Index(
                        name = "idx_performance_event_provider",
                        columnList = "provider_id"
                ),
                @Index(
                        name = "idx_performance_event_type",
                        columnList = "event_type"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_performance_event_id",
                        columnNames = "event_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProviderPerformanceEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            name = "event_id",
            nullable = false,
            unique = true,
            length = 100
    )
    private String eventId;

    @Column(
            name = "provider_id",
            nullable = false
    )
    private Long providerId;

    @Column(
            name = "event_type",
            nullable = false,
            length = 50
    )
    private String eventType;

    @Column(name = "response_time_seconds")
    private Double responseTimeSeconds;

    @Column(
            name = "created_at",
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}