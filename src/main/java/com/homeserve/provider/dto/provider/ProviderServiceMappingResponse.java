package com.homeserve.provider.dto.provider;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProviderServiceMappingResponse {

    private Long id;

    private Long providerId;

    private Long serviceId;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}