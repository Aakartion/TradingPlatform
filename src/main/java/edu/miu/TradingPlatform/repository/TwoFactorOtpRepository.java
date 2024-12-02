package edu.miu.TradingPlatform.repository;

import edu.miu.TradingPlatform.domain.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP, String> {
    Optional<TwoFactorOTP> findByUser_UserId(Long userUserId);
}
