package edu.miu.TradingPlatform.dto.forgotPasswordToken.request;

public record ResetPasswordRequestDTO(
        String otp,
        String password
) {}
