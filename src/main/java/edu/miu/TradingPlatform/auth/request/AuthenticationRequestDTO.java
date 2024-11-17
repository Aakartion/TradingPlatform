package edu.miu.TradingPlatform.auth.request;

public record AuthenticationRequestDTO(
        String userEmail,
        String userPassword
) {
}
