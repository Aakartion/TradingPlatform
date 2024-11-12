package edu.miu.TradingPlatform.domain;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class TwoFactorAuthentication {
    private boolean isTwoFactorAuthenticationEnabled = false;
    private VerificationType sendTo;
}
