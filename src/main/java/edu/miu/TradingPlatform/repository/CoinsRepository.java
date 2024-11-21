package edu.miu.TradingPlatform.repository;

import edu.miu.TradingPlatform.domain.Coins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinsRepository extends JpaRepository<Coins, String> {
}
