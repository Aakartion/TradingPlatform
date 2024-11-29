package edu.miu.TradingPlatform.dto.users.response;

import edu.miu.TradingPlatform.domain.TwoFactorAuthentication;
import edu.miu.TradingPlatform.domain.USER_ROLE;

public record UserResponseDTO(
    String userFirstName,
    String userLastName,
    String userEmail,
    USER_ROLE userRole,
    TwoFactorAuthentication twoFactorAuthentication) {}
