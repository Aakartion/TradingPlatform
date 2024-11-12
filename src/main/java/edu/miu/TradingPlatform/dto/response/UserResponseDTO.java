package edu.miu.TradingPlatform.dto.response;

import edu.miu.TradingPlatform.domain.USER_ROLE;

public record UserResponseDTO(
        String userFirstName,
        String userLastName,
        String userEmail,
        USER_ROLE userRole
) {
}
