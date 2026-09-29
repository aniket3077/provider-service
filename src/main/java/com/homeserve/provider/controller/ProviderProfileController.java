package com.homeserve.provider.controller;

import com.homeserve.provider.dto.common.ApiResponse;
import com.homeserve.provider.dto.ProviderProfileResponse;
import com.homeserve.provider.dto.ProviderProfileUpdateRequest;
import com.homeserve.provider.service.ProviderProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProviderProfileController {

    private final ProviderProfileService providerProfileService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<ProviderProfileResponse>> getMyProfile() {

        ProviderProfileResponse response =
                providerProfileService.getMyProfile();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Provider profile fetched successfully",
                        response
                )
        );
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<ProviderProfileResponse>> updateMyProfile(
            @Valid @RequestBody ProviderProfileUpdateRequest request) {

        ProviderProfileResponse response =
                providerProfileService.updateMyProfile(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Provider profile updated successfully",
                        response
                )
        );
    }
}