package edu.miu.TradingPlatform.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class TwoFactorOTP {
  @Id private String twoFactorOtpId;
  private String otpCode;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @OneToOne
  private User user;

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String jwtToken;
}
