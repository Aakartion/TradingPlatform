package edu.miu.TradingPlatform.repository;

import edu.miu.TradingPlatform.domain.ForgotPasswordToken;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.response.ForgotPasswordTokenResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordToken, String> {
//    ForgotPasswordTokenResponse findByUser_UserId(Long userUserId);
}
