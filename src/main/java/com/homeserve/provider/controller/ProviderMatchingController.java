package com.homeserve.provider.controller;

import com.homeserve.provider.dto.matching.ProviderCandidateResponse;
import com.homeserve.provider.service.ProviderMatchingService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/providers")
@RequiredArgsConstructor
public class ProviderMatchingController {

    private final ProviderMatchingService matchingService;

    @GetMapping("/matching-candidates")
    public ResponseEntity<List<ProviderCandidateResponse>>
    getMatchingCandidates(

            @RequestParam Long serviceId,

            @RequestParam Double latitude,

            @RequestParam Double longitude,

            @RequestParam Double radiusKm,

            @RequestParam LocalDate requestedDate,

            @RequestParam LocalTime requestedStartTime,

            @RequestParam LocalTime requestedEndTime
    ) {
        System.err.println(
                "🔥🔥🔥 PROVIDER MATCHING ENDPOINT HIT 🔥🔥🔥"
        );

        List<ProviderCandidateResponse> candidates =
                matchingService.getMatchingCandidates(
                        serviceId,
                        latitude,
                        longitude,
                        radiusKm,
                        requestedDate,
                        requestedStartTime,
                        requestedEndTime
                );

        return ResponseEntity.ok(candidates);
    }
}