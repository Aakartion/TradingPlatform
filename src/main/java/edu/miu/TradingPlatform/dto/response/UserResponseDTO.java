package edu.miu.TradingPlatform.dto.response;

public record UserResponseDTO(
        String userFirstName,
        String userLastName,
        String userEmail
) {
}
