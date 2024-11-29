package edu.miu.TradingPlatform.service.user;

import edu.miu.TradingPlatform.domain.VerificationType;
import edu.miu.TradingPlatform.dto.users.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.users.response.UserResponseDTO;

public interface UserService {

  UserResponseDTO findUserByJwtToken(String jwtToken) throws Exception;

  UserResponseDTO findUserByEmail(String email);

  UserRequestDTO findUserByUserId(Long userId);

  UserResponseDTO enableTwoFactorAuthentication(
      VerificationType verificationType, String sendTo, UserRequestDTO userRequestDTO);

  UserResponseDTO updateUserPassword(UserRequestDTO userRequestDTO, String newPassword);
}
