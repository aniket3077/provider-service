package com.homeserve.provider.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String token;

    private String tokenType;

    private Long providerId;

    private String firstName;

    private String lastName;

    private String email;

    private String status;
}