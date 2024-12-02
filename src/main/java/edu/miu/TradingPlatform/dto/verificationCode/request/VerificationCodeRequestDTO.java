package edu.miu.TradingPlatform.dto.verificationCode.request;

import edu.miu.TradingPlatform.domain.VerificationType;
import edu.miu.TradingPlatform.dto.users.request.UserRequestDTO;


public record VerificationCodeRequestDTO(
    String otp,
    UserRequestDTO userRequestDTO,
    String email,
    String phoneNumber,
    VerificationType verificationType) {}
