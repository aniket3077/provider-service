package com.homeserve.provider.service;

import com.homeserve.provider.dto.ProviderPerformanceEventRequest;
import com.homeserve.provider.dto.performance.ProviderPerformanceResponse;
import com.homeserve.provider.entity.Provider;
import com.homeserve.provider.entity.ProviderPerformance;
import com.homeserve.provider.entity.ProviderPerformanceEvent;
import com.homeserve.provider.repository.ProviderPerformanceEventRepository;
import com.homeserve.provider.repository.ProviderPerformanceRepository;
import com.homeserve.provider.repository.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProviderPerformanceService {

    private final ProviderPerformanceRepository performanceRepository;
    private final ProviderRepository providerRepository;

    private final CurrentProviderService currentProviderService;

    private final ProviderPerformanceEventRepository eventRepository;

    @Transactional
    public ProviderPerformance getOrCreate(Long providerId) {

        return performanceRepository.findByProviderId(providerId)
                .orElseGet(() -> {

                    Provider provider = providerRepository.findById(providerId)
                            .orElseThrow(() ->
                                    new RuntimeException(
                                            "Provider not found with id: " + providerId
                                    ));

                    ProviderPerformance performance =
                            ProviderPerformance.builder()
                                    .provider(provider)
                                    .totalOffers(0)
                                    .acceptedOffers(0)
                                    .rejectedOffers(0)
                                    .expiredOffers(0)
                                    .totalJobs(0)
                                    .completedJobs(0)
                                    .cancelledJobs(0)
                                    .averageResponseTimeSeconds(0.0)
                                    .activeJobs(0)
                                    .updatedAt(LocalDateTime.now())
                                    .build();

                    return performanceRepository.save(performance);
                });
    }

    @Transactional
    public void recordOfferSent(Long providerId) {

        ProviderPerformance performance =
                getOrCreate(providerId);

        performance.setTotalOffers(
                performance.getTotalOffers() + 1
        );

        save(performance);
    }

    @Transactional
    public void recordOfferAccepted(
            Long providerId,
            double responseTimeSeconds) {

        ProviderPerformance performance =
                getOrCreate(providerId);

        performance.setAcceptedOffers(
                performance.getAcceptedOffers() + 1
        );

        performance.setActiveJobs(
                performance.getActiveJobs() + 1
        );

        updateAverageResponseTime(
                performance,
                responseTimeSeconds
        );

        save(performance);
    }

    @Transactional
    public void recordOfferRejected(
            Long providerId,
            double responseTimeSeconds) {

        ProviderPerformance performance =
                getOrCreate(providerId);

        performance.setRejectedOffers(
                performance.getRejectedOffers() + 1
        );

        updateAverageResponseTime(
                performance,
                responseTimeSeconds
        );

        save(performance);
    }

    @Transactional
    public void recordOfferExpired(Long providerId) {

        ProviderPerformance performance =
                getOrCreate(providerId);

        performance.setExpiredOffers(
                performance.getExpiredOffers() + 1
        );

        save(performance);
    }

    @Transactional
    public void recordJobCompleted(Long providerId) {

        ProviderPerformance performance =
                getOrCreate(providerId);

        performance.setTotalJobs(
                performance.getTotalJobs() + 1
        );

        performance.setCompletedJobs(
                performance.getCompletedJobs() + 1
        );

        decreaseActiveJobs(performance);

        save(performance);
    }

    @Transactional
    public void recordJobCancelled(Long providerId) {

        ProviderPerformance performance =
                getOrCreate(providerId);

        performance.setTotalJobs(
                performance.getTotalJobs() + 1
        );

        performance.setCancelledJobs(
                performance.getCancelledJobs() + 1
        );

        decreaseActiveJobs(performance);

        save(performance);
    }

    @Transactional(readOnly = true)
    public ProviderPerformanceResponse getMyPerformance() {

        Provider provider =
                currentProviderService.getCurrentProvider();

        ProviderPerformance performance =
                performanceRepository
                        .findByProviderId(provider.getId())
                        .orElseGet(() ->
                                getOrCreate(provider.getId()));

        return mapToResponse(performance);
    }

    private void decreaseActiveJobs(
            ProviderPerformance performance) {

        if (performance.getActiveJobs() > 0) {
            performance.setActiveJobs(
                    performance.getActiveJobs() - 1
            );
        }
    }

    private void updateAverageResponseTime(
            ProviderPerformance performance,
            double newResponseTime) {

        int respondedOffers =
                performance.getAcceptedOffers()
                        + performance.getRejectedOffers();

        if (respondedOffers <= 0) {
            performance.setAverageResponseTimeSeconds(
                    newResponseTime
            );
            return;
        }

        double currentAverage =
                performance.getAverageResponseTimeSeconds();

        double newAverage =
                ((currentAverage * (respondedOffers - 1))
                        + newResponseTime)
                        / respondedOffers;

        performance.setAverageResponseTimeSeconds(
                newAverage
        );
    }

    private void save(ProviderPerformance performance) {

        performance.setUpdatedAt(LocalDateTime.now());

        performanceRepository.save(performance);
    }

    private ProviderPerformanceResponse mapToResponse(
            ProviderPerformance performance) {

        double acceptanceRate =
                calculateRate(
                        performance.getAcceptedOffers(),
                        performance.getTotalOffers()
                );

        double completionRate =
                calculateRate(
                        performance.getCompletedJobs(),
                        performance.getTotalJobs()
                );

        double cancellationRate =
                calculateRate(
                        performance.getCancelledJobs(),
                        performance.getTotalJobs()
                );

        return ProviderPerformanceResponse.builder()
                .providerId(
                        performance.getProvider().getId()
                )
                .totalOffers(
                        performance.getTotalOffers()
                )
                .acceptedOffers(
                        performance.getAcceptedOffers()
                )
                .rejectedOffers(
                        performance.getRejectedOffers()
                )
                .expiredOffers(
                        performance.getExpiredOffers()
                )
                .totalJobs(
                        performance.getTotalJobs()
                )
                .completedJobs(
                        performance.getCompletedJobs()
                )
                .cancelledJobs(
                        performance.getCancelledJobs()
                )
                .acceptanceRate(acceptanceRate)
                .completionRate(completionRate)
                .cancellationRate(cancellationRate)
                .averageResponseTimeSeconds(
                        performance.getAverageResponseTimeSeconds()
                )
                .activeJobs(
                        performance.getActiveJobs()
                )
                .build();
    }

    private double calculateRate(
            int numerator,
            int denominator) {

        if (denominator == 0) {
            return 0.0;
        }

        return ((double) numerator / denominator) * 100.0;
    }

    @Transactional
    public void recordEvent(
            ProviderPerformanceEventRequest request) {

        // 1. Check whether event was already processed
        if (eventRepository.existsByEventId(request.getEventId())) {
            return;
        }

        // 2. Verify provider exists
        providerRepository.findById(request.getProviderId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Provider not found with ID: "
                                        + request.getProviderId()
                        ));

        // 3. Process performance event
        switch (request.getEventType().toUpperCase()) {

            case "OFFER_SENT":

                recordOfferSent(
                        request.getProviderId()
                );

                break;

            case "OFFER_ACCEPTED":

                recordOfferAccepted(
                        request.getProviderId(),
                        getResponseTime(request)
                );

                break;

            case "OFFER_REJECTED":

                recordOfferRejected(
                        request.getProviderId(),
                        getResponseTime(request)
                );

                break;

            case "OFFER_EXPIRED":

                recordOfferExpired(
                        request.getProviderId()
                );

                break;

            case "JOB_COMPLETED":

                recordJobCompleted(
                        request.getProviderId()
                );

                break;

            case "JOB_CANCELLED":

                recordJobCancelled(
                        request.getProviderId()
                );

                break;

            default:

                throw new IllegalArgumentException(
                        "Unsupported performance event: "
                                + request.getEventType()
                );
        }

        // 4. Save event only after performance update
        ProviderPerformanceEvent event =
                ProviderPerformanceEvent.builder()
                        .eventId(request.getEventId())
                        .providerId(request.getProviderId())
                        .eventType(
                                request.getEventType().toUpperCase()
                        )
                        .responseTimeSeconds(
                                request.getResponseTimeSeconds()
                        )
                        .build();

        eventRepository.save(event);
    }

    private double getResponseTime(
            ProviderPerformanceEventRequest request) {

        if (request.getResponseTimeSeconds() == null) {
            throw new IllegalArgumentException(
                    "Response time is required for offer response events"
            );
        }

        return request.getResponseTimeSeconds();
    }
}