package edu.miu.TradingPlatform.service.forgotPasswordToken;


import edu.miu.TradingPlatform.domain.ForgotPasswordToken;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.VerificationType;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.request.ForgotPasswordTokenRequest;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.response.ForgotPasswordTokenResponse;
import edu.miu.TradingPlatform.dto.users.request.UserRequestDTO;

public interface ForgotPasswordTokenService {
    ForgotPasswordToken createForgotPasswordToken (User user,
                                                           String forgotPasswordTokenId,
                                                           String Otp,
                                                           VerificationType verificationType,
                                                           String sendTo);

    ForgotPasswordToken findForgotPasswordVerificationId(String verificationId);

    ForgotPasswordToken findByUserId(Long userId);

    void deleteForgotPasswordToken(ForgotPasswordTokenRequest forgotPasswordTokenRequest);


}
