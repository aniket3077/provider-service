package com.homeserve.provider.dto.availability;

import com.homeserve.provider.entity.AvailabilityStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class ProviderAvailabilityResponse {

    private Long id;

    private Long providerId;

    private LocalDate availableDate;

    private LocalTime startTime;

    private LocalTime endTime;

    private AvailabilityStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}