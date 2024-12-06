package edu.miu.TradingPlatform.repository;

import edu.miu.TradingPlatform.domain.WatchList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchListRepository extends JpaRepository<WatchList, Long> {
    WatchList findByUser_UserId(Long userId);
}
