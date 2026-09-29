package com.homeserve.provider.controller;

import com.homeserve.provider.dto.common.ApiResponse;
import com.homeserve.provider.dto.ProviderPerformanceEventRequest;
import com.homeserve.provider.service.ProviderPerformanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/providers/performance")
@RequiredArgsConstructor
public class ProviderPerformanceInternalController {

    private final ProviderPerformanceService performanceService;

    @PostMapping("/event")
    public ResponseEntity<ApiResponse<Void>> recordEvent(
            @Valid @RequestBody ProviderPerformanceEventRequest request) {

        performanceService.recordEvent(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Provider performance event recorded",
                        null
                )
        );
    }
}