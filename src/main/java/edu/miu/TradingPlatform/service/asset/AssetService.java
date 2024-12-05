package edu.miu.TradingPlatform.service.asset;

import edu.miu.TradingPlatform.domain.Assets;
import edu.miu.TradingPlatform.domain.Coins;
import edu.miu.TradingPlatform.domain.User;

import java.util.List;

public interface AssetService {
    Assets createAsset(User user, Coins coin, double quantity);

    Assets getAssetById(Long assetId) throws Exception;
    Assets getAssetByUserIdAndAssetId(Long userId, Long assetId);
    List<Assets> getAssetsByUserId(String jwtToken);
    Assets updateAsset(Long assetId, double quantity) throws Exception;

    Assets findAssetByCoinId(String jwtToken, String coinId);

    void deleteAsset(Long assetId);
}
