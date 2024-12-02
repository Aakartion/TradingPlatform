package edu.miu.TradingPlatform.service.user;

import edu.miu.TradingPlatform.auth.response.AuthenticationResponseDTO;
import edu.miu.TradingPlatform.domain.VerificationType;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.request.ForgotPasswordTokenRequest;
import edu.miu.TradingPlatform.dto.users.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.users.response.UserResponseDTO;

public interface UserService {

  UserResponseDTO getUserProfile(String jwtToken);
  UserResponseDTO sendVerificationOtp(String jwtToken, VerificationType verificationType);
  UserResponseDTO verifyTwoFactorAuthentication(String jwtToken, String otp) throws Exception;

  UserResponseDTO findUserByEmail(String email);

  UserRequestDTO findUserByUserId(Long userId);

  UserResponseDTO updateUserPassword(UserRequestDTO userRequestDTO, String newPassword);

//  AuthenticationResponseDTO sendForgotPasswordOtp(ForgotPasswordTokenRequest forgotPasswordTokenRequest);
}
