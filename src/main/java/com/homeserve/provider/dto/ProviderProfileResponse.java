package com.homeserve.provider.dto;

import com.homeserve.provider.entity.ProviderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProviderProfileResponse {

    private Long providerId;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private ProviderStatus status;
    private Boolean verified;

    private Integer experienceYears;
    private String profileImageUrl;
}