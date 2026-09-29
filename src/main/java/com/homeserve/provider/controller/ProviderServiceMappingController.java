package com.homeserve.provider.controller;

import com.homeserve.provider.dto.common.ApiResponse;
import com.homeserve.provider.dto.provider.ProviderServiceMappingRequest;
import com.homeserve.provider.dto.provider.ProviderServiceMappingResponse;
import com.homeserve.provider.service.ProviderServiceMappingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/providers/me/services")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProviderServiceMappingController {

    private final ProviderServiceMappingService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ProviderServiceMappingResponse>>
    addMyService(
            @Valid @RequestBody ProviderServiceMappingRequest request) {

        ProviderServiceMappingResponse response =
                service.addMyService(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Service added successfully",
                        response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProviderServiceMappingResponse>>>
    getMyServices() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Provider services fetched successfully",
                        service.getMyServices()));
    }

    @PutMapping("/{serviceId}/activate")
    public ResponseEntity<ApiResponse<ProviderServiceMappingResponse>>
    activateMyService(
            @PathVariable Long serviceId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Service activated successfully",
                        service.activateMyService(serviceId)));
    }

    @PutMapping("/{serviceId}/deactivate")
    public ResponseEntity<ApiResponse<ProviderServiceMappingResponse>>
    deactivateMyService(
            @PathVariable Long serviceId) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Service deactivated successfully",
                        service.deactivateMyService(serviceId)));
    }

    @DeleteMapping("/{serviceId}")
    public ResponseEntity<ApiResponse<Void>>
    deleteMyService(
            @PathVariable Long serviceId) {

        service.deleteMyService(serviceId);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Service removed successfully",
                        null));
    }
}