package com.homeserve.provider.controller;

import com.homeserve.provider.dto.common.ApiResponse;
import com.homeserve.provider.dto.performance.ProviderPerformanceResponse;
import com.homeserve.provider.service.ProviderPerformanceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/providers/me/performance")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ProviderPerformanceController {

    private final ProviderPerformanceService performanceService;

    @GetMapping
    public ResponseEntity<ApiResponse<ProviderPerformanceResponse>>
    getMyPerformance() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Provider performance fetched successfully",
                        performanceService.getMyPerformance()
                )
        );
    }
}