package edu.miu.TradingPlatform.repository;

import edu.miu.TradingPlatform.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
    Optional<VerificationCode> findByVerificationCodeId(Long verificationCodeId);

    Optional<VerificationCode> findVerificationCodeByUser_UserId(Long userUserId);
}
