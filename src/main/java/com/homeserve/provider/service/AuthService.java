package com.homeserve.provider.service;

import com.homeserve.provider.dto.auth.LoginRequest;
import com.homeserve.provider.dto.auth.LoginResponse;
import com.homeserve.provider.dto.auth.RegisterRequest;
import com.homeserve.provider.dto.auth.RegisterResponse;
import com.homeserve.provider.entity.Provider;
import com.homeserve.provider.entity.ProviderStatus;
import com.homeserve.provider.repository.ProviderRepository;
import com.homeserve.provider.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ProviderRepository providerRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    @Transactional
    public RegisterResponse register(
            RegisterRequest request
    ) {

        String email =
                request.getEmail()
                        .trim()
                        .toLowerCase();

        String phone =
                request.getPhone()
                        .trim();

        if (providerRepository.existsByEmail(email)) {
            throw new RuntimeException(
                    "Provider with this email already exists"
            );
        }

        if (providerRepository.existsByPhone(phone)) {
            throw new RuntimeException(
                    "Provider with this phone number already exists"
            );
        }

        Provider provider =
                Provider.builder()
                        .firstName(
                                request.getFirstName().trim()
                        )
                        .lastName(
                                request.getLastName().trim()
                        )
                        .email(email)
                        .phone(phone)
                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()
                                )
                        )
                        .status(
                                ProviderStatus.PENDING_VERIFICATION
                        )
                        .verified(false)
                        .experienceYears(
                                request.getExperienceYears()
                        )
                        .profileImageUrl(
                                request.getProfileImageUrl()
                        )
                        .build();

        Provider savedProvider =
                providerRepository.save(provider);

        return RegisterResponse.builder()
                .providerId(savedProvider.getId())
                .firstName(savedProvider.getFirstName())
                .lastName(savedProvider.getLastName())
                .email(savedProvider.getEmail())
                .phone(savedProvider.getPhone())
                .status(savedProvider.getStatus())
                .verified(savedProvider.getVerified())
                .build();
    }

    public LoginResponse login(
            LoginRequest request
    ) {

        String email =
                request.getEmail()
                        .trim()
                        .toLowerCase();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        request.getPassword()
                )
        );

        Provider provider =
                providerRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Provider not found"
                                )
                        );

        UserDetails userDetails =
                userDetailsService
                        .loadUserByUsername(email);

        String token =
                jwtService.generateToken(
                        userDetails
                );

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .providerId(provider.getId())
                .firstName(provider.getFirstName())
                .lastName(provider.getLastName())
                .email(provider.getEmail())
                .status(provider.getStatus().name())
                .build();
    }
}