package edu.miu.TradingPlatform.service.verification;

import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.VerificationCode;
import edu.miu.TradingPlatform.domain.VerificationType;
import edu.miu.TradingPlatform.dto.users.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.verificationCode.request.VerificationCodeRequestDTO;
import edu.miu.TradingPlatform.dto.verificationCode.response.VerificationCodeResponseDTO;

public interface VerificationCodeService {
  VerificationCode sendVerificationCode(
          User user, VerificationType verificationType);

  VerificationCode getVerificationCodeByVerificationCodeId(Long verificationCodeId);

  VerificationCode getVerificationCodeByUserId(Long userId);

  void deleteVerificationCodeByVerificationCodeId(Long verificationCodeId);

  boolean verificationOtp(String otp, VerificationCode verificationCode);
}
