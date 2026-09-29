package com.homeserve.provider.dto.rating;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProviderRatingRequest {

    @NotNull(message = "Booking ID is required")
    private Long bookingId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Rating is required")
    @DecimalMin(
            value = "1.0",
            message = "Rating must be at least 1.0"
    )
    @DecimalMax(
            value = "5.0",
            message = "Rating cannot exceed 5.0"
    )
    private BigDecimal rating;

    @Size(
            max = 1000,
            message = "Review cannot exceed 1000 characters"
    )
    private String review;
}