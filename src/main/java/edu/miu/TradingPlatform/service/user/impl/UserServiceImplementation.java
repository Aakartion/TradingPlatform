package edu.miu.TradingPlatform.service.user.impl;

import edu.miu.TradingPlatform.auth.response.AuthenticationResponseDTO;
import edu.miu.TradingPlatform.auth.response.ForgotPasswordApiResponse;
import edu.miu.TradingPlatform.config.authentication.JwtService;
import edu.miu.TradingPlatform.domain.*;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.request.ForgotPasswordTokenRequest;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.request.ResetPasswordRequestDTO;
import edu.miu.TradingPlatform.dto.users.response.UserResponseDTO;
import edu.miu.TradingPlatform.exception.InValidOTPException;
import edu.miu.TradingPlatform.exception.ResourcesNotFoundException;
import edu.miu.TradingPlatform.mapper.UserMapper;
import edu.miu.TradingPlatform.mapper.VerificationCodeMapper;
import edu.miu.TradingPlatform.repository.UserRepository;
import edu.miu.TradingPlatform.service.email.EmailService;
import edu.miu.TradingPlatform.service.forgotPasswordToken.ForgotPasswordTokenService;
import edu.miu.TradingPlatform.service.user.UserService;
import edu.miu.TradingPlatform.service.verification.VerificationCodeService;
import edu.miu.TradingPlatform.utils.OtpGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserServiceImplementation implements UserService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final VerificationCodeService verificationCodeService;
  private final ForgotPasswordTokenService forgotPasswordTokenService;
  private final UserMapper userMapper;
  private final VerificationCodeMapper verificationCodeMapper;
  private final EmailService emailService;

  public UserServiceImplementation(
      UserRepository userRepository,
      JwtService jwtService,
      VerificationCodeService verificationCodeService,
      ForgotPasswordTokenService forgotPasswordTokenService,
      UserMapper userMapper,
      VerificationCodeMapper verificationCodeMapper,
      EmailService emailService) {
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.verificationCodeService = verificationCodeService;
    this.forgotPasswordTokenService = forgotPasswordTokenService;
    this.userMapper = userMapper;
    this.verificationCodeMapper = verificationCodeMapper;
    this.emailService = emailService;
  }

  @Override
  public UserResponseDTO getUserProfile(String jwtToken) {
    User user = findUserByJwtToken(jwtToken);
    UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(user);
    return userResponseDTO;
  }

  public User findUserByJwtToken(String jwtToken) {
    if (jwtToken == null || jwtToken.trim().isEmpty()) {
      throw new IllegalArgumentException("JWT token cannot be null or empty");
    }
    jwtToken = jwtToken.trim();
    String email;
    try {
      email = jwtService.getEmailFromJwtToken(jwtToken);
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid JWT token", e);
    }
    return userRepository
        .findByUserEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Override
  public UserResponseDTO sendVerificationOtp (String jwtToken, VerificationType verificationType) {
    User user = findUserByJwtToken(jwtToken);
    VerificationCode verificationCode = null;
    try{
      verificationCode = verificationCodeService.getVerificationCodeByUserId(user.getUserId());
    } catch (ResourcesNotFoundException e){
      System.out.println("Verification code not found, creating a new one...");
    }
    if (verificationCode == null) {
      verificationCode = verificationCodeService.sendVerificationCode(user, verificationType);
    }
    if (verificationType.equals(VerificationType.EMAIL)) {
      emailService.sendVerificationOtpEmail(user.getUserEmail(), verificationCode.getOtp());
    }
    return userMapper.userToUserResponseDTO(user);
  }

  @Override
  public UserResponseDTO verifyTwoFactorAuthentication(String jwtToken, String otp)
      throws Exception {
    User user = findUserByJwtToken(jwtToken);
    VerificationCode verificationCode =
        verificationCodeService.getVerificationCodeByUserId(user.getUserId());
    String sendTo =
        verificationCode.getVerificationType().equals(VerificationType.EMAIL)
            ? verificationCode.getEmail()
            : verificationCode.getPhoneNumber();

    boolean isVerified = verificationCode.getOtp().equals(otp);
    if (isVerified) {
      UserResponseDTO updatedUserResponseDto =
          enableTwoFactorAuthentication(verificationCode.getVerificationType(), sendTo, user);
      verificationCodeService.deleteVerificationCodeByVerificationCodeId(
          verificationCode.getVerificationCodeId());
      return updatedUserResponseDto;
    }
    throw new InValidOTPException("Wrong OTP is provided");
  }

  private UserResponseDTO enableTwoFactorAuthentication(
      VerificationType verificationType, String sendTo, User user) {
    TwoFactorAuthentication twoFactorAuthentication = new TwoFactorAuthentication();
    twoFactorAuthentication.setTwoFactorAuthenticationEnabled(true);
    twoFactorAuthentication.setSendTo(verificationType);
    user.setTwoFactorAuthentication(twoFactorAuthentication);
    userRepository.save(user);
    UserResponseDTO userResponseDTO = userMapper.userToUserResponseDTO(user);
    return userResponseDTO;
  }

  @Override
  public AuthenticationResponseDTO sendForgotPasswordOtp(
      ForgotPasswordTokenRequest forgotPasswordTokenRequest) {
    User user = findUserByJwtToken(forgotPasswordTokenRequest.sendTo());
    String otp = OtpGenerator.generateOtp();
    UUID uuid = UUID.randomUUID();
    String forgotPasswordTokenId = uuid.toString();

    ForgotPasswordToken forgotPasswordToken =
        forgotPasswordTokenService.findByUserId(user.getUserId());

    if (forgotPasswordToken == null) {
      forgotPasswordToken =
          forgotPasswordTokenService.createForgotPasswordToken(
              user,
              forgotPasswordTokenId,
              otp,
              forgotPasswordTokenRequest.verificationType(),
              forgotPasswordTokenRequest.sendTo());
    }
    if(forgotPasswordTokenRequest.verificationType().equals(VerificationType.EMAIL)) {
      emailService.sendVerificationOtpEmail(user.getUserEmail(), otp);
    }

    AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
    authenticationResponseDTO.setSession(forgotPasswordToken.getForgotPasswordTokenId());
    authenticationResponseDTO.setMessage("Password reset OTP sent successfully");
    return authenticationResponseDTO;
  }

  @Override
  public ForgotPasswordApiResponse resetPassword(String forgotPasswordTokenId, ResetPasswordRequestDTO resetPasswordRequestDTO, String jwtToken) {
    ForgotPasswordToken forgotPasswordToken = forgotPasswordTokenService.findForgotPasswordVerificationId(forgotPasswordTokenId);
    boolean isVerified = forgotPasswordToken.getOtp().equals(resetPasswordRequestDTO.otp());
    if(isVerified) {
      updateUserPassword(forgotPasswordToken.getUser(), resetPasswordRequestDTO.password());
      ForgotPasswordApiResponse forgotPasswordApiResponse = new ForgotPasswordApiResponse("Password updated successfully");
      return forgotPasswordApiResponse;
    }
    throw new InValidOTPException("Wrong OTP is provided");
  }

  private User updateUserPassword(User user, String newPassword) {
    user.setUserPassword(newPassword);
    return userRepository.save(user);
  }
}
