package com.homeserve.provider.dto.notification;

import com.homeserve.provider.entity.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDeviceRequest {

    @NotBlank
    private String deviceToken;

    @NotNull
    private DeviceType deviceType;
}