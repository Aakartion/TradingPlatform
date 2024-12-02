package edu.miu.TradingPlatform.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ForgotPasswordToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String forgotPasswordTokenId;
  @OneToOne
  private User user;
  private String otp;
  private VerificationType verificationType;
  private String sendTo;
}
