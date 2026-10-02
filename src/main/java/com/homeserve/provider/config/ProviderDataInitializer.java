package com.homeserve.provider.config;

import com.homeserve.provider.entity.*;
import com.homeserve.provider.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ProviderDataInitializer implements CommandLineRunner {

    private final ProviderRepository providerRepository;
    private final ProviderLocationRepository locationRepository;
    private final ProviderAvailabilityRepository availabilityRepository;
    private final ProviderServiceMappingRepository serviceMappingRepository;
    private final ProviderPerformanceRepository performanceRepository;
    private final ProviderRatingRepository ratingRepository;

    private final PasswordEncoder passwordEncoder;

    /*
     * Test password for all seeded providers.
     *
     * Login:
     * provider1@gmail.com
     * provider2@gmail.com
     * ...
     *
     * Password:
     * Provider@123
     */
    private static final String DEFAULT_PASSWORD = "Provider@123";

    /*
     * Service IDs must match the service IDs
     * present in Customer/Service Catalog.
     *
     * Example:
     *
     * 1 = Plumbing
     * 2 = Electrical
     * 3 = AC Service
     * 4 = Cleaning
     * 5 = Appliance Repair
     */
    private static final Long PLUMBING_SERVICE = 1L;
    private static final Long ELECTRICAL_SERVICE = 2L;
    private static final Long AC_SERVICE = 3L;
    private static final Long CLEANING_SERVICE = 4L;
    private static final Long APPLIANCE_SERVICE = 5L;


    @Override
    @Transactional
    public void run(String... args) {

        log.info("========================================");
        log.info("Provider data initialization started");
        log.info("========================================");

        createProvider1();
        createProvider2();
        createProvider3();
        createProvider4();
        createProvider5();

        log.info("========================================");
        log.info("Provider data initialization completed");
        log.info("========================================");
    }


    // =========================================================
    // PROVIDER 1
    // =========================================================

    private void createProvider1() {

        String email = "provider1@gmail.com";

        if (providerRepository.existsByEmail(email)) {
            log.info("{} already exists. Skipping.", email);
            return;
        }

        Provider provider = createProvider(
                "Rahul",
                "Patil",
                email,
                "9876543201",
                6
        );

        /*
         * Near test location.
         */
        createLocation(
                provider,
                19.9975000,
                73.7898000
        );

        createService(provider, PLUMBING_SERVICE);
        createService(provider, ELECTRICAL_SERVICE);

        createAvailability(provider);

        createPerformance(
                provider,
                100,
                92,
                5,
                3,
                85,
                80,
                3,
                14.0,
                1
        );

        createRatings(
                provider,
                10001L,
                4.9,
                "Excellent service"
        );

        createRatings(
                provider,
                10002L,
                4.8,
                "Very professional"
        );

        createRatings(
                provider,
                10003L,
                5.0,
                "Excellent work"
        );
    }


    // =========================================================
    // PROVIDER 2
    // =========================================================

    private void createProvider2() {

        String email = "provider2@gmail.com";

        if (providerRepository.existsByEmail(email)) {
            log.info("{} already exists. Skipping.", email);
            return;
        }

        Provider provider = createProvider(
                "Amit",
                "Shinde",
                email,
                "9876543202",
                5
        );

        createLocation(
                provider,
                19.9990000,
                73.7920000
        );

        createService(provider, PLUMBING_SERVICE);
        createService(provider, AC_SERVICE);

        createAvailability(provider);

        createPerformance(
                provider,
                100,
                88,
                8,
                4,
                80,
                74,
                4,
                18.0,
                0
        );

        createRatings(
                provider,
                20001L,
                4.7,
                "Good service"
        );

        createRatings(
                provider,
                20002L,
                4.8,
                "Quick response"
        );

        createRatings(
                provider,
                20003L,
                4.6,
                "Professional work"
        );
    }


    // =========================================================
    // PROVIDER 3
    // =========================================================

    private void createProvider3() {

        String email = "provider3@gmail.com";

        if (providerRepository.existsByEmail(email)) {
            log.info("{} already exists. Skipping.", email);
            return;
        }

        Provider provider = createProvider(
                "Sagar",
                "Jadhav",
                email,
                "9876543203",
                8
        );

        createLocation(
                provider,
                20.0020000,
                73.7950000
        );

        createService(provider, PLUMBING_SERVICE);
        createService(provider, ELECTRICAL_SERVICE);
        createService(provider, AC_SERVICE);

        createAvailability(provider);

        createPerformance(
                provider,
                120,
                105,
                10,
                5,
                95,
                88,
                4,
                11.0,
                2
        );

        createRatings(
                provider,
                30001L,
                4.9,
                "Highly recommended"
        );

        createRatings(
                provider,
                30002L,
                4.9,
                "Excellent technician"
        );

        createRatings(
                provider,
                30003L,
                4.8,
                "Good experience"
        );
    }


    // =========================================================
    // PROVIDER 4
    // =========================================================

    private void createProvider4() {

        String email = "provider4@gmail.com";

        if (providerRepository.existsByEmail(email)) {
            log.info("{} already exists. Skipping.", email);
            return;
        }

        Provider provider = createProvider(
                "Vishal",
                "More",
                email,
                "9876543204",
                4
        );

        createLocation(
                provider,
                20.0100000,
                73.8010000
        );

        createService(provider, CLEANING_SERVICE);
        createService(provider, APPLIANCE_SERVICE);

        createAvailability(provider);

        createPerformance(
                provider,
                60,
                45,
                10,
                5,
                42,
                35,
                5,
                30.0,
                0
        );

        createRatings(
                provider,
                40001L,
                4.3,
                "Good service"
        );

        createRatings(
                provider,
                40002L,
                4.5,
                "Satisfied"
        );
    }


    // =========================================================
    // PROVIDER 5
    // =========================================================

    private void createProvider5() {

        String email = "provider5@gmail.com";

        if (providerRepository.existsByEmail(email)) {
            log.info("{} already exists. Skipping.", email);
            return;
        }

        Provider provider = createProvider(
                "Akash",
                "Pawar",
                email,
                "9876543205",
                7
        );

        createLocation(
                provider,
                20.0150000,
                73.8080000
        );

        createService(provider, ELECTRICAL_SERVICE);
        createService(provider, AC_SERVICE);
        createService(provider, APPLIANCE_SERVICE);

        createAvailability(provider);

        createPerformance(
                provider,
                90,
                78,
                7,
                5,
                70,
                65,
                3,
                16.0,
                1
        );

        createRatings(
                provider,
                50001L,
                4.6,
                "Very good service"
        );

        createRatings(
                provider,
                50002L,
                4.7,
                "Professional technician"
        );
    }


    // =========================================================
    // CREATE PROVIDER
    // =========================================================

    private Provider createProvider(
            String firstName,
            String lastName,
            String email,
            String phone,
            Integer experienceYears
    ) {

        Provider provider =
                Provider.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .phone(phone)
                        .password(
                                passwordEncoder.encode(
                                        DEFAULT_PASSWORD
                                )
                        )
                        .status(ProviderStatus.ACTIVE)
                        .verified(true)
                        .experienceYears(experienceYears)
                        .build();

        provider =
                providerRepository.save(provider);

        log.info(
                "Created provider: {} {} | ID: {}",
                firstName,
                lastName,
                provider.getId()
        );

        return provider;
    }


    // =========================================================
    // CREATE LOCATION
    // =========================================================

    private void createLocation(
            Provider provider,
            double latitude,
            double longitude
    ) {

        ProviderLocation location =
                ProviderLocation.builder()
                        .provider(provider)
                        .latitude(
                                BigDecimal.valueOf(latitude)
                        )
                        .longitude(
                                BigDecimal.valueOf(longitude)
                        )
                        .build();

        locationRepository.save(location);
    }


    // =========================================================
    // CREATE SERVICE MAPPING
    // =========================================================

    private void createService(
            Provider provider,
            Long serviceId
    ) {

        ProviderServiceMapping mapping =
                ProviderServiceMapping.builder()
                        .provider(provider)
                        .serviceId(serviceId)
                        .active(true)
                        .build();

        serviceMappingRepository.save(mapping);
    }


    // =========================================================
    // CREATE AVAILABILITY
    // =========================================================

    private void createAvailability(
            Provider provider
    ) {

        /*
         * Creates test availability from:
         *
         * 30 days before today
         *          ↓
         * 90 days after today
         *
         * Useful while testing bookings with multiple dates.
         */

        LocalDate startDate =
                LocalDate.now().minusDays(30);

        LocalDate endDate =
                LocalDate.now().plusDays(90);

        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {

            ProviderAvailability availability =
                    ProviderAvailability.builder()
                            .provider(provider)
                            .availableDate(currentDate)
                            .startTime(
                                    LocalTime.of(8, 0)
                            )
                            .endTime(
                                    LocalTime.of(20, 0)
                            )
                            .status(
                                    AvailabilityStatus.AVAILABLE
                            )
                            .build();

            availabilityRepository.save(
                    availability
            );

            currentDate =
                    currentDate.plusDays(1);
        }
    }


    // =========================================================
    // CREATE PERFORMANCE
    // =========================================================

    private void createPerformance(
            Provider provider,
            int totalOffers,
            int acceptedOffers,
            int rejectedOffers,
            int expiredOffers,
            int totalJobs,
            int completedJobs,
            int cancelledJobs,
            double averageResponseTimeSeconds,
            int activeJobs
    ) {

        ProviderPerformance performance =
                ProviderPerformance.builder()
                        .provider(provider)
                        .totalOffers(totalOffers)
                        .acceptedOffers(acceptedOffers)
                        .rejectedOffers(rejectedOffers)
                        .expiredOffers(expiredOffers)
                        .totalJobs(totalJobs)
                        .completedJobs(completedJobs)
                        .cancelledJobs(cancelledJobs)
                        .averageResponseTimeSeconds(
                                averageResponseTimeSeconds
                        )
                        .activeJobs(activeJobs)
                        .build();

        performanceRepository.save(performance);
    }


    // =========================================================
    // CREATE RATING
    // =========================================================

    private void createRatings(
            Provider provider,
            Long bookingId,
            double rating,
            String review
    ) {

        ProviderRating providerRating =
                ProviderRating.builder()
                        .provider(provider)
                        .bookingId(bookingId)

                        /*
                         * Dummy customer ID used only
                         * for development testing.
                         */
                        .customerId(1L)

                        .rating(
                                BigDecimal.valueOf(rating)
                        )
                        .review(review)
                        .build();

        ratingRepository.save(providerRating);
    }
}