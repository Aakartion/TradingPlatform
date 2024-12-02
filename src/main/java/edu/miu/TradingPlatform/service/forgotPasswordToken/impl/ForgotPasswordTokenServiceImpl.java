package edu.miu.TradingPlatform.service.forgotPasswordToken.impl;

import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.VerificationType;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.request.ForgotPasswordTokenRequest;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.response.ForgotPasswordTokenResponse;
import edu.miu.TradingPlatform.dto.users.request.UserRequestDTO;
import edu.miu.TradingPlatform.service.forgotPasswordToken.ForgotPasswordTokenService;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordTokenServiceImpl implements ForgotPasswordTokenService {

    @Override
    public ForgotPasswordTokenResponse createForgotPasswordToken(User user, String forgotPasswordTokenId, String Otp, VerificationType verificationType, String sendTo) {
        return null;
    }

    @Override
    public ForgotPasswordTokenResponse findForgotPasswordVerificationId(String verificationId) {
        return null;
    }

    @Override
    public ForgotPasswordTokenResponse findByUserId(Long userId) {
        return null;
    }

    @Override
    public void deleteForgotPasswordToken(ForgotPasswordTokenRequest forgotPasswordTokenRequest) {

    }
}