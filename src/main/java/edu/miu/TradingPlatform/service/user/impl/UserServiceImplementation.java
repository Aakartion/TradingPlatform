package edu.miu.TradingPlatform.service.user.impl;

import edu.miu.TradingPlatform.config.authentication.JwtService;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.VerificationType;
import edu.miu.TradingPlatform.dto.users.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.users.response.UserResponseDTO;
import edu.miu.TradingPlatform.mapper.UserMapper;
import edu.miu.TradingPlatform.repository.UserRepository;
import edu.miu.TradingPlatform.service.user.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final UserMapper userMapper;

  public UserServiceImplementation(
      UserRepository userRepository, JwtService jwtService, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.userMapper = userMapper;
  }

  @Override
  public UserResponseDTO findUserByJwtToken(String jwtToken) throws Exception {

    if (jwtToken == null || jwtToken.trim().isEmpty()) {
      throw new IllegalArgumentException("JWT token cannot be null or empty");
    }
    jwtToken = jwtToken.trim();

    String email = jwtService.getEmailFromJwtToken(jwtToken);

    Optional<User> user = userRepository.findByUserEmail(email);
    if (user.isPresent()) {
      return userMapper.userToUserResponseDTO(user.get());
    }
    throw new UsernameNotFoundException("User not found");
  }

  @Override
  public UserResponseDTO findUserByEmail(String email) {
    return null;
  }

  @Override
  public UserRequestDTO findUserByUserId(Long userId) {
    return null;
  }

  @Override
  public UserResponseDTO enableTwoFactorAuthentication(
      VerificationType verificationType, String sendTo, UserRequestDTO userRequestDTO) {
    return null;
  }

  @Override
  public UserResponseDTO updateUserPassword(UserRequestDTO userRequestDTO, String newPassword) {
    return null;
  }
}
