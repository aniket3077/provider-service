package com.homeserve.provider.controller;

import com.homeserve.provider.dto.common.ApiResponse;
import com.homeserve.provider.dto.offer.ProviderOfferActionResponse;
import com.homeserve.provider.service.ProviderOfferActionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        "/api/providers/me/offers"
)
@RequiredArgsConstructor
@SecurityRequirement(
        name = "bearerAuth"
)
public class ProviderOfferActionController {

    private final ProviderOfferActionService
            providerOfferActionService;


    // =========================================================
    // ACCEPT
    // =========================================================

    @PostMapping("/{offerId}/accept")
    public ResponseEntity<
            ApiResponse<ProviderOfferActionResponse>>
    acceptOffer(

            @PathVariable Long offerId
    ) {

        ProviderOfferActionResponse response =
                providerOfferActionService
                        .acceptOffer(
                                offerId
                        );


        return ResponseEntity.ok(
                ApiResponse.success(
                        response.getMessage(),
                        response
                )
        );
    }


    // =========================================================
    // REJECT
    // =========================================================

    @PostMapping("/{offerId}/reject")
    public ResponseEntity<
            ApiResponse<ProviderOfferActionResponse>>
    rejectOffer(

            @PathVariable Long offerId
    ) {

        ProviderOfferActionResponse response =
                providerOfferActionService
                        .rejectOffer(
                                offerId
                        );


        return ResponseEntity.ok(
                ApiResponse.success(
                        response.getMessage(),
                        response
                )
        );
    }
}