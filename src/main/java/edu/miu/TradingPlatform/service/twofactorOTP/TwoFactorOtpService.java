package edu.miu.TradingPlatform.service.twofactorOTP;

import edu.miu.TradingPlatform.domain.TwoFactorOTP;
import edu.miu.TradingPlatform.domain.User;

public interface TwoFactorOtpService {
  TwoFactorOTP createTwoFactorOtp(User user, String otpCode, String jwtToken);

  TwoFactorOTP findByUserId(Long userId);

  TwoFactorOTP findByOtpId(String twoFactorOtpId);

  boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otpCode);

  void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp);
}
