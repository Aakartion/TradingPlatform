package edu.miu.TradingPlatform.service.twofactorOTP.impl;

import edu.miu.TradingPlatform.domain.TwoFactorOTP;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.repository.TwoFactorOtpRepository;
import edu.miu.TradingPlatform.service.twofactorOTP.TwoFactorOtpService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorOtpServiceImpl implements TwoFactorOtpService {

  private final TwoFactorOtpRepository twoFactorOtpRepository;

  public TwoFactorOtpServiceImpl(TwoFactorOtpRepository twoFactorOtpRepository) {
    this.twoFactorOtpRepository = twoFactorOtpRepository;
  }

  @Override
  public TwoFactorOTP createTwoFactorOtp(User user, String otpCode, String jwtToken) {
    UUID uuid = UUID.randomUUID();
    String twoFactorOtpId = uuid.toString();
    TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
    twoFactorOTP.setTwoFactorOtpId(twoFactorOtpId);
    twoFactorOTP.setOtpCode(otpCode);
    twoFactorOTP.setJwtToken(jwtToken);
    twoFactorOTP.setUser(user);
    return twoFactorOtpRepository.save(twoFactorOTP);
  }

  @Override
  public TwoFactorOTP findByUserId(Long userId) {
    Optional<TwoFactorOTP> optionalTwoFactorOTP = twoFactorOtpRepository.findByUser_UserId(userId);
    if (optionalTwoFactorOTP.isPresent()) {
      return optionalTwoFactorOTP.get();
    }
    return null;
  }

  @Override
  public TwoFactorOTP findByOtpId(String twoFactorOtpId) {
    Optional<TwoFactorOTP> searchOtp = twoFactorOtpRepository.findById(twoFactorOtpId);
    if (searchOtp.isPresent()) {
      return searchOtp.get();
    }
    return null;
  }

  @Override
  public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otpCode) {
    return twoFactorOtp.getOtpCode().equals(otpCode);
  }

  @Override
  public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp) {
    twoFactorOtpRepository.delete(twoFactorOtp);
  }
}
