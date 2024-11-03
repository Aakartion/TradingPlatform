package edu.miu.TradingPlatform.domain;

import lombok.Data;

@Data
public class TwoFactorAuthentication {
    private boolean isTwoFactorAuthenticationEnabled = false;
    private VerificationType sendTo;
}
