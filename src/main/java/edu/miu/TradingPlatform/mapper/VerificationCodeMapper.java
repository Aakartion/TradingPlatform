package edu.miu.TradingPlatform.mapper;

import edu.miu.TradingPlatform.domain.VerificationCode;
import edu.miu.TradingPlatform.dto.verificationCode.response.VerificationCodeResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VerificationCodeMapper {
  VerificationCodeResponseDTO verificationCodeToVerificationCodeResponseDTO(
      VerificationCode verificationCode);

  VerificationCode verificationCodeResponseDTOToVerificationCode(VerificationCodeResponseDTO verificationCodeResponseDTO);
}
