package com.homeserve.provider.dto.location;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ProviderLocationResponse {

    private Long providerId;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private LocalDateTime updatedAt;
}