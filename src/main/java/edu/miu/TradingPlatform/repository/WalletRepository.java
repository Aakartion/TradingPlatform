package edu.miu.TradingPlatform.repository;

import edu.miu.TradingPlatform.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByUser_UserId(Long userId);

}
