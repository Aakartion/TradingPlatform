package edu.miu.TradingPlatform.dto.users.request;

import edu.miu.TradingPlatform.domain.USER_ROLE;

public record UserRequestDTO(
        String firstName,
        String lastName,
        String email,
        String password,
        USER_ROLE role
) {
}
