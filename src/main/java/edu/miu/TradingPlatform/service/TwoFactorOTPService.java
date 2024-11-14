package edu.miu.TradingPlatform.service;

import edu.miu.TradingPlatform.domain.Users;
import edu.miu.TradingPlatform.dto.response.UserLoginResponseDTO;
import edu.miu.TradingPlatform.model.TwoFactorOTP;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface TwoFactorOTPService {

    TwoFactorOTP createTwoFactorOtp(Users user, String otp, String jwtToken);

    TwoFactorOTP findByUser(Long userId);

    TwoFactorOTP findById(String id);

    boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp);

    void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP);

    UserLoginResponseDTO verifySignInOTP(String otp,String id) throws Exception;
}
