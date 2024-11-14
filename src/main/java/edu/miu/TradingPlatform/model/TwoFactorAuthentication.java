package edu.miu.TradingPlatform.model;

import edu.miu.TradingPlatform.domain.VerificationType;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class TwoFactorAuthentication {
    private boolean isTwoFactorAuthenticationEnabled = false;
    private VerificationType sendTo;
}
