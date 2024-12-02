package edu.miu.TradingPlatform.service.forgotPasswordToken;


import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.VerificationType;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.request.ForgotPasswordTokenRequest;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.response.ForgotPasswordTokenResponse;
import edu.miu.TradingPlatform.dto.users.request.UserRequestDTO;

public interface ForgotPasswordTokenService {
    ForgotPasswordTokenResponse createForgotPasswordToken (User user,
                                                           String forgotPasswordTokenId,
                                                           String Otp,
                                                           VerificationType verificationType,
                                                           String sendTo);

    ForgotPasswordTokenResponse findForgotPasswordVerificationId(String verificationId);

    ForgotPasswordTokenResponse findByUserId(Long userId);

    void deleteForgotPasswordToken(ForgotPasswordTokenRequest forgotPasswordTokenRequest);


}
