package com.homeserve.provider.client;

import com.homeserve.provider.dto.offer.InternalOfferActionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "customer-service"
)
public interface CustomerProviderOfferClient {

    String INTERNAL_API_KEY_HEADER =
            "X-Internal-Api-Key";


    // =========================================================
    // ACCEPT
    // =========================================================

    @PostMapping(
            "/internal/provider-offers/{offerId}/accept"
    )
    InternalOfferActionResponse acceptOffer(

            @PathVariable("offerId")
            Long offerId,

            @RequestParam("providerId")
            Long providerId,

            @RequestHeader(
                    INTERNAL_API_KEY_HEADER
            )
            String internalApiKey
    );


    // =========================================================
    // REJECT
    // =========================================================

    @PostMapping(
            "/internal/provider-offers/{offerId}/reject"
    )
    InternalOfferActionResponse rejectOffer(

            @PathVariable("offerId")
            Long offerId,

            @RequestParam("providerId")
            Long providerId,

            @RequestHeader(
                    INTERNAL_API_KEY_HEADER
            )
            String internalApiKey
    );
}