package edu.miu.TradingPlatform.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long verificationCodeId;
    private String otp;
    @OneToOne
    private User user;
    private String email;
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private VerificationType verificationType;
}
