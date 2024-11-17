package edu.miu.TradingPlatform.auth.response;

public record AuthenticationResponseDTO(
        String jwtToken,
        boolean status,
        String message,
        boolean isTwoFactorAuthenticationEnabled,
        String session
) {
}
