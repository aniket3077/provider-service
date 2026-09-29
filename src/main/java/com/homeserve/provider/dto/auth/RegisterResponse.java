package com.homeserve.provider.dto.auth;

import com.homeserve.provider.entity.ProviderStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {

    private Long providerId;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private ProviderStatus status;

    private Boolean verified;
}