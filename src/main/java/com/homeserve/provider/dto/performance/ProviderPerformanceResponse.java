package com.homeserve.provider.dto.performance;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProviderPerformanceResponse {

    private Long providerId;

    private Integer totalOffers;
    private Integer acceptedOffers;
    private Integer rejectedOffers;
    private Integer expiredOffers;

    private Integer totalJobs;
    private Integer completedJobs;
    private Integer cancelledJobs;

    private Double acceptanceRate;
    private Double completionRate;
    private Double cancellationRate;

    private Double averageResponseTimeSeconds;

    private Integer activeJobs;
}