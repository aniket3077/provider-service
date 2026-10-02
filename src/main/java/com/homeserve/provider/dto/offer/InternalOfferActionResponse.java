package com.homeserve.provider.dto.offer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InternalOfferActionResponse {

    private boolean success;

    private String message;
}