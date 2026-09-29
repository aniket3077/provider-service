package com.homeserve.provider.controller;

import com.homeserve.provider.dto.common.ApiResponse;
import com.homeserve.provider.dto.rating.ProviderRatingResponse;
import com.homeserve.provider.service.ProviderRatingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/providers/me/ratings")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProviderRatingController {

    private final ProviderRatingService ratingService;

    @GetMapping
    public ResponseEntity<
            ApiResponse<List<ProviderRatingResponse>>> getMyRatings() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Provider ratings fetched successfully",
                        ratingService.getMyRatings()
                )
        );
    }

    @GetMapping("/average")
    public ResponseEntity<ApiResponse<BigDecimal>>
    getMyAverageRating() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Provider average rating fetched successfully",
                        ratingService.getMyAverageRating()
                )
        );
    }
}