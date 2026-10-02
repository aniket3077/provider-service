package com.homeserve.provider.dto.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProviderOfferActionResponse {

    private Long offerId;

    private Long providerId;

    private String action;

    private String message;
}