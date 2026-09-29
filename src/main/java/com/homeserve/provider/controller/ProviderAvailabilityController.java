package com.homeserve.provider.controller;

import com.homeserve.provider.dto.common.ApiResponse;
import com.homeserve.provider.dto.availability.ProviderAvailabilityRequest;
import com.homeserve.provider.dto.availability.ProviderAvailabilityResponse;
import com.homeserve.provider.entity.AvailabilityStatus;
import com.homeserve.provider.service.ProviderAvailabilityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/providers/me/availability")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProviderAvailabilityController {

    private final ProviderAvailabilityService availabilityService;

    @PostMapping
    public ResponseEntity<
            ApiResponse<ProviderAvailabilityResponse>> addAvailability(
            @Valid @RequestBody ProviderAvailabilityRequest request) {

        ProviderAvailabilityResponse response =
                availabilityService.addMyAvailability(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                "Availability added successfully",
                                response
                        )
                );
    }

    @GetMapping
    public ResponseEntity<
            ApiResponse<List<ProviderAvailabilityResponse>>> getAvailability(
            @RequestParam(required = false) LocalDate date) {

        List<ProviderAvailabilityResponse> response =
                availabilityService.getMyAvailability(date);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Availability fetched successfully",
                        response
                )
        );
    }

    @PutMapping("/{availabilityId}")
    public ResponseEntity<
            ApiResponse<ProviderAvailabilityResponse>> updateAvailability(
            @PathVariable Long availabilityId,
            @Valid @RequestBody ProviderAvailabilityRequest request) {

        ProviderAvailabilityResponse response =
                availabilityService.updateMyAvailability(
                        availabilityId,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Availability updated successfully",
                        response
                )
        );
    }

    @PutMapping("/{availabilityId}/status")
    public ResponseEntity<
            ApiResponse<ProviderAvailabilityResponse>> updateStatus(
            @PathVariable Long availabilityId,
            @RequestParam AvailabilityStatus status) {

        ProviderAvailabilityResponse response =
                availabilityService.updateMyAvailabilityStatus(
                        availabilityId,
                        status
                );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Availability status updated successfully",
                        response
                )
        );
    }

    @DeleteMapping("/{availabilityId}")
    public ResponseEntity<ApiResponse<Void>> deleteAvailability(
            @PathVariable Long availabilityId) {

        availabilityService.deleteMyAvailability(
                availabilityId
        );

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Availability deleted successfully",
                        null
                )
        );
    }
}