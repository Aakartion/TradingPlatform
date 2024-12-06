package edu.miu.TradingPlatform.repository;

import edu.miu.TradingPlatform.domain.Withdrawal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawalRepository extends JpaRepository<Withdrawal, Long> {
    List<Withdrawal> findByUser_UserId(long userId);
}
