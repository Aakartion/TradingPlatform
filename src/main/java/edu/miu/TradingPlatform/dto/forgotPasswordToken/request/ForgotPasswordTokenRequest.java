package edu.miu.TradingPlatform.dto.forgotPasswordToken.request;

import edu.miu.TradingPlatform.domain.VerificationType;

public record ForgotPasswordTokenRequest(
        String sendTo,
        VerificationType verificationType
) {}
