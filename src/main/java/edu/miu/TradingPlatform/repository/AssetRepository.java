package edu.miu.TradingPlatform.repository;

import edu.miu.TradingPlatform.domain.Assets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Assets, Long> {
    List<Assets> findAssetsByUser_UserId(Long userUserId);

    Assets findAssetsByUser_UserIdAndCoins_Id(Long userUserId, String coinsId);

}