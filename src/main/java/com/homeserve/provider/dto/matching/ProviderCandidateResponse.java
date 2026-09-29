package com.homeserve.provider.dto.matching;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProviderCandidateResponse {

    private Long providerId;

    private Double latitude;

    private Double longitude;

    private Double rating;

    private Double acceptanceRate;

    private Double completionRate;

    private Double cancellationRate;

    private Double averageResponseTimeSeconds;

    private Integer activeJobs;

    private Boolean available;

    private Boolean verified;
}