package edu.miu.TradingPlatform.auth.response;

import lombok.Data;

@Data
public class AuthenticationResponseDTO {
  private String jwtToken;
  private boolean status;
  private String message;
  private boolean isTwoFactorAuthenticationEnabled;
  private String session;
}
