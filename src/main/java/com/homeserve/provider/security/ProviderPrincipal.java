package com.homeserve.provider.security;

import lombok.Getter;

@Getter
public class ProviderPrincipal {

    private final Long providerId;
    private final String email;

    public ProviderPrincipal(Long providerId, String email) {
        this.providerId = providerId;
        this.email = email;
    }
}