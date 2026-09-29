package com.homeserve.provider.service;

import com.homeserve.provider.dto.rating.ProviderRatingResponse;
import com.homeserve.provider.entity.Provider;
import com.homeserve.provider.entity.ProviderRating;
import com.homeserve.provider.repository.ProviderRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderRatingService {

    private final ProviderRatingRepository ratingRepository;
    private final CurrentProviderService currentProviderService;

    @Transactional(readOnly = true)
    public BigDecimal getMyAverageRating() {

        Provider provider =
                currentProviderService.getCurrentProvider();

        BigDecimal average =
                ratingRepository.findAverageRating(provider.getId());

        return average != null
                ? average
                : BigDecimal.ZERO;
    }

    @Transactional(readOnly = true)
    public List<ProviderRatingResponse> getMyRatings() {

        Provider provider =
                currentProviderService.getCurrentProvider();

        return ratingRepository
                .findByProviderIdOrderByCreatedAtDesc(provider.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private ProviderRatingResponse mapToResponse(
            ProviderRating rating) {

        return ProviderRatingResponse.builder()
                .id(rating.getId())
                .providerId(rating.getProvider().getId())
                .bookingId(rating.getBookingId())
                .rating(rating.getRating())
                .review(rating.getReview())
                .createdAt(rating.getCreatedAt())
                .build();
    }
}