package edu.miu.TradingPlatform.repositorie;

import edu.miu.TradingPlatform.model.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwoFactorOTPRepository extends JpaRepository<TwoFactorOTP, String> {

//    TwoFactorOTP findByUserId(Long userId);
    TwoFactorOTP findByUser_UserId (Long userId);
}
