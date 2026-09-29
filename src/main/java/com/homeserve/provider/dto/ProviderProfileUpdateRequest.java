package com.homeserve.provider.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderProfileUpdateRequest {

    @NotBlank(message = "First name is required")
    @Size(max = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    private String lastName;

    @NotBlank(message = "Phone is required")
    @Size(max = 20)
    private String phone;

    private Integer experienceYears;

    @Size(max = 500)
    private String profileImageUrl;
}