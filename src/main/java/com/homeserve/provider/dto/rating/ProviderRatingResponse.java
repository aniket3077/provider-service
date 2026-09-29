package com.homeserve.provider.dto.rating;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ProviderRatingResponse {

    private Long id;

    private Long providerId;

    private Long bookingId;

    private Long customerId;

    private BigDecimal rating;

    private String review;

    private LocalDateTime createdAt;
}