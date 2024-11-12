package edu.miu.TradingPlatform.dto.request;


public record UserRequestDTO(
        String userFirstName,
        String userLastName,
        String userEmail,
        String userPassword
        ) {
}