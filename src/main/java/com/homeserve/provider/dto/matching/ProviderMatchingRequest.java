package com.homeserve.provider.dto.matching;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ProviderMatchingRequest {

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    @NotNull(message = "Customer latitude is required")
    @DecimalMin(value = "-90.0", message = "Invalid latitude")
    @DecimalMax(value = "90.0", message = "Invalid latitude")
    private Double latitude;

    @NotNull(message = "Customer longitude is required")
    @DecimalMin(value = "-180.0", message = "Invalid longitude")
    @DecimalMax(value = "180.0", message = "Invalid longitude")
    private Double longitude;

    @NotNull(message = "Radius is required")
    @DecimalMin(value = "0.1", message = "Radius must be greater than 0")
    private Double radiusKm;

    @NotNull(message = "Requested date is required")
    private LocalDate requestedDate;

    @NotNull(message = "Requested start time is required")
    private LocalTime requestedStartTime;

    @NotNull(message = "Requested end time is required")
    private LocalTime requestedEndTime;
}