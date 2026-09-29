package com.homeserve.provider.controller;

import com.homeserve.provider.dto.common.ApiResponse;
import com.homeserve.provider.dto.location.ProviderLocationRequest;
import com.homeserve.provider.dto.location.ProviderLocationResponse;
import com.homeserve.provider.service.ProviderLocationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/providers/me/location")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProviderLocationController {

    private final ProviderLocationService locationService;

    @PutMapping
    public ResponseEntity<ApiResponse<ProviderLocationResponse>>
    updateMyLocation(
            @Valid @RequestBody ProviderLocationRequest request) {

        ProviderLocationResponse response =
                locationService.updateMyLocation(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Provider location updated successfully",
                        response
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ProviderLocationResponse>>
    getMyLocation() {

        ProviderLocationResponse response =
                locationService.getMyLocation();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Provider location fetched successfully",
                        response
                )
        );
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>>
    deleteMyLocation() {

        locationService.deleteMyLocation();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Provider location deleted successfully",
                        null
                )
        );
    }
}