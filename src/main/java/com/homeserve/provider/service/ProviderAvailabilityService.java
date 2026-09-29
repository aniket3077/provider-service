package com.homeserve.provider.service;

import com.homeserve.provider.dto.availability.ProviderAvailabilityRequest;
import com.homeserve.provider.dto.availability.ProviderAvailabilityResponse;
import com.homeserve.provider.entity.AvailabilityStatus;
import com.homeserve.provider.entity.Provider;
import com.homeserve.provider.entity.ProviderAvailability;
import com.homeserve.provider.repository.ProviderAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderAvailabilityService {

    private final ProviderAvailabilityRepository availabilityRepository;
    private final CurrentProviderService currentProviderService;

    @Transactional
    public ProviderAvailabilityResponse addMyAvailability(
            ProviderAvailabilityRequest request) {

        Provider provider = currentProviderService.getCurrentProvider();

        validateTime(request.getStartTime(), request.getEndTime());

        ProviderAvailability availability =
                ProviderAvailability.builder()
                        .provider(provider)
                        .availableDate(request.getAvailableDate())
                        .startTime(request.getStartTime())
                        .endTime(request.getEndTime())
                        .status(AvailabilityStatus.AVAILABLE)
                        .build();

        ProviderAvailability saved =
                availabilityRepository.save(availability);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ProviderAvailabilityResponse> getMyAvailability(
            LocalDate date) {

        Provider provider = currentProviderService.getCurrentProvider();

        List<ProviderAvailability> availability;

        if (date != null) {
            availability =
                    availabilityRepository
                            .findByProviderIdAndAvailableDate(
                                    provider.getId(),
                                    date
                            );
        } else {
            availability =
                    availabilityRepository
                            .findByProviderId(provider.getId());
        }

        return availability.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public ProviderAvailabilityResponse updateMyAvailability(
            Long availabilityId,
            ProviderAvailabilityRequest request) {

        Provider provider = currentProviderService.getCurrentProvider();

        ProviderAvailability availability =
                availabilityRepository.findById(availabilityId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Availability not found"
                                ));

        // Very important: verify ownership
        if (!availability.getProvider().getId()
                .equals(provider.getId())) {

            throw new RuntimeException(
                    "You cannot modify another provider's availability"
            );
        }

        validateTime(
                request.getStartTime(),
                request.getEndTime()
        );

        availability.setAvailableDate(
                request.getAvailableDate()
        );

        availability.setStartTime(
                request.getStartTime()
        );

        availability.setEndTime(
                request.getEndTime()
        );

        ProviderAvailability updated =
                availabilityRepository.save(availability);

        return mapToResponse(updated);
    }

    @Transactional
    public ProviderAvailabilityResponse updateMyAvailabilityStatus(
            Long availabilityId,
            AvailabilityStatus status) {

        Provider provider = currentProviderService.getCurrentProvider();

        ProviderAvailability availability =
                availabilityRepository.findById(availabilityId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Availability not found"
                                ));

        if (!availability.getProvider().getId()
                .equals(provider.getId())) {

            throw new RuntimeException(
                    "You cannot modify another provider's availability"
            );
        }

        availability.setStatus(status);

        return mapToResponse(
                availabilityRepository.save(availability)
        );
    }

    @Transactional
    public void deleteMyAvailability(Long availabilityId) {

        Provider provider = currentProviderService.getCurrentProvider();

        ProviderAvailability availability =
                availabilityRepository.findById(availabilityId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Availability not found"
                                ));

        if (!availability.getProvider().getId()
                .equals(provider.getId())) {

            throw new RuntimeException(
                    "You cannot delete another provider's availability"
            );
        }

        availabilityRepository.delete(availability);
    }

    private void validateTime(
            LocalTime startTime,
            LocalTime endTime) {

        if (startTime == null || endTime == null) {
            throw new RuntimeException(
                    "Start time and end time are required"
            );
        }

        if (!startTime.isBefore(endTime)) {
            throw new RuntimeException(
                    "Start time must be before end time"
            );
        }
    }

    private ProviderAvailabilityResponse mapToResponse(
            ProviderAvailability availability) {

        return ProviderAvailabilityResponse.builder()
                .id(availability.getId())
                .providerId(
                        availability.getProvider().getId()
                )
                .availableDate(
                        availability.getAvailableDate()
                )
                .startTime(
                        availability.getStartTime()
                )
                .endTime(
                        availability.getEndTime()
                )
                .status(
                        availability.getStatus()
                )
                .createdAt(
                        availability.getCreatedAt()
                )
                .updatedAt(
                        availability.getUpdatedAt()
                )
                .build();
    }
}