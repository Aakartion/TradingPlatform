package edu.miu.TradingPlatform.dto.response;

import lombok.Data;

@Data
public class UserLoginResponseDTO {
    private String jwtToken;
    private boolean status;
    private String message;
    private boolean isTwoFactorAuthEnabled;
    private String session;
}