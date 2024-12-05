package edu.miu.TradingPlatform.service.user;

import edu.miu.TradingPlatform.auth.response.AuthenticationResponseDTO;
import edu.miu.TradingPlatform.auth.response.ForgotPasswordApiResponse;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.VerificationType;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.request.ForgotPasswordTokenRequest;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.request.ResetPasswordRequestDTO;
import edu.miu.TradingPlatform.dto.users.response.UserResponseDTO;

public interface UserService {

  UserResponseDTO getUserProfile(String jwtToken);

  UserResponseDTO sendVerificationOtp(String jwtToken, VerificationType verificationType);

  UserResponseDTO verifyTwoFactorAuthentication(String jwtToken, String otp) throws Exception;

  AuthenticationResponseDTO sendForgotPasswordOtp(
      ForgotPasswordTokenRequest forgotPasswordTokenRequest);

  ForgotPasswordApiResponse resetPassword(
      String forgotPasswordTokenId,
      ResetPasswordRequestDTO resetPasswordRequestDTO,
      String jwtToken);

  User findUserByJwtToken(String jwtToken);
}
