package com.homeserve.provider.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderPerformanceEventRequest {

    @NotBlank(message = "Event ID is required")
    private String eventId;

    @NotNull(message = "Provider ID is required")
    private Long providerId;

    @NotBlank(message = "Event type is required")
    private String eventType;

    private Double responseTimeSeconds;
}