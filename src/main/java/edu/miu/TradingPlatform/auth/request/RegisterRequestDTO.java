package edu.miu.TradingPlatform.auth.request;

public record RegisterRequestDTO(
        String userFirstName,
        String userLastName,
        String userEmail,
        String userPassword
) {
}
