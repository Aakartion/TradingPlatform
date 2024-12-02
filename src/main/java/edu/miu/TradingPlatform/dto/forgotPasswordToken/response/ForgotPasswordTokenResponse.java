package edu.miu.TradingPlatform.dto.forgotPasswordToken.response;

import edu.miu.TradingPlatform.domain.VerificationType;

public record ForgotPasswordTokenResponse(
    String email, String otp, VerificationType verificationType, String sendTo) {}
