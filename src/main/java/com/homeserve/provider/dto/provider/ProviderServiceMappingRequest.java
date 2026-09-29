package com.homeserve.provider.dto.provider;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderServiceMappingRequest {

    @NotNull(message = "Service ID is required")
    private Long serviceId;
}