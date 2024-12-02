package edu.miu.TradingPlatform.service.verification.impl;

import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.VerificationCode;
import edu.miu.TradingPlatform.domain.VerificationType;
import edu.miu.TradingPlatform.dto.users.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.verificationCode.request.VerificationCodeRequestDTO;
import edu.miu.TradingPlatform.dto.verificationCode.response.VerificationCodeResponseDTO;

import edu.miu.TradingPlatform.exception.ResourcesNotFoundException;
import edu.miu.TradingPlatform.mapper.UserMapper;
import edu.miu.TradingPlatform.mapper.VerificationCodeMapper;
import edu.miu.TradingPlatform.repository.UserRepository;
import edu.miu.TradingPlatform.repository.VerificationCodeRepository;
import edu.miu.TradingPlatform.service.verification.VerificationCodeService;
import edu.miu.TradingPlatform.utils.OtpGenerator;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

  private final VerificationCodeRepository verificationCodeRepository;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final VerificationCodeMapper verificationCodeMapper;

  public VerificationCodeServiceImpl(
          VerificationCodeRepository verificationCodeRepository, UserRepository userRepository,
          UserMapper userMapper,
          VerificationCodeMapper verificationCodeMapper) {
    this.verificationCodeRepository = verificationCodeRepository;
      this.userRepository = userRepository;
      this.userMapper = userMapper;
    this.verificationCodeMapper = verificationCodeMapper;
  }

  @Override
  @Transactional
  public VerificationCode sendVerificationCode(
      User user, VerificationType verificationType) {
    VerificationCode verificationCode = new VerificationCode();
    Optional<User> optionalUser = userRepository.findByUserEmail(user.getUserEmail());
    if(optionalUser.isEmpty()) {
     throw new ResourcesNotFoundException("User not found *****");
    }
    User entityUser = optionalUser.get();
    verificationCode.setVerificationType(verificationType);
    verificationCode.setUser(entityUser);
    verificationCode.setOtp(OtpGenerator.generateOtp());
    verificationCodeRepository.save(verificationCode);
    return verificationCode;
  }

  @Override
  public VerificationCode getVerificationCodeByVerificationCodeId(
      Long verificationCodeId) {
      Optional<VerificationCode> verificationCode = verificationCodeRepository.findByVerificationCodeId(verificationCodeId);
      if (verificationCode.isPresent()) {
          return verificationCode.get();
      }
    throw new ResourcesNotFoundException( "!!!!VerificationCode Id: " + verificationCodeId + " not found");
  }

  @Override
  public VerificationCode getVerificationCodeByUserId(Long userId) {
    Optional<VerificationCode> verificationCode = verificationCodeRepository.findVerificationCodeByUser_UserId(userId);
    if(verificationCode.isPresent()) {
      return verificationCode.get();
    }
    throw new ResourcesNotFoundException( "VerificationCode for user: " + userId + " not found");
  }

  @Override
  public void deleteVerificationCodeByVerificationCodeId(Long verificationCodeId) {
    System.out.println("THis is verification Code ID:::" + verificationCodeId);
    Optional<VerificationCode> verificationCode = verificationCodeRepository.findByVerificationCodeId(verificationCodeId);
    if(verificationCode.isPresent()) {
      verificationCodeRepository.delete(verificationCode.get());
    }
    throw new ResourcesNotFoundException( "@@@@VerificationCode Id: " + verificationCodeId + " not found");
  }

  @Override
  public boolean verificationOtp(
      String otp, VerificationCode verificationCode) {
    return false;
  }
}
