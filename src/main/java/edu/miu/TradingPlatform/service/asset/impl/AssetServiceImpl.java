package edu.miu.TradingPlatform.service.asset.impl;

import edu.miu.TradingPlatform.domain.Assets;
import edu.miu.TradingPlatform.domain.Coins;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.exception.ResourcesNotFoundException;
import edu.miu.TradingPlatform.repository.AssetRepository;
import edu.miu.TradingPlatform.service.asset.AssetService;
import edu.miu.TradingPlatform.service.user.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final UserService userService;

    public AssetServiceImpl(AssetRepository assetRepository, UserService userService) {
        this.assetRepository = assetRepository;
        this.userService = userService;
    }

    @Override
    public Assets createAsset(User user, Coins coin, double quantity) {
        Assets asset = new Assets();
        asset.setUser(user);
        asset.setCoins(coin);
        asset.setAssetQuantity(quantity);
        asset.setAssetBuyPrice(asset.getAssetQuantity() * quantity);
        return assetRepository.save(asset);
    }

    @Override
    public Assets getAssetById(Long assetId) throws Exception {
        return assetRepository.findById(assetId).orElseThrow(()-> new ResourcesNotFoundException("Asset not found"));
    }

    @Override
    public Assets getAssetByUserIdAndAssetId(Long userId, Long assetId) {
        return null;
    }

    @Override
    public List<Assets> getAssetsByUserId(String jwtToken) {
        User user = userService.findUserByJwtToken(jwtToken);
        return assetRepository.findAssetsByUser_UserId(user.getUserId());
    }

    @Override
    public Assets updateAsset(Long assetId, double quantity) throws Exception {
        Assets oldAsset = getAssetById(assetId);
        oldAsset.setAssetQuantity(quantity + oldAsset.getAssetQuantity());
        return assetRepository.save(oldAsset);
    }

    @Override
    public Assets findAssetByCoinId(String jwtToken, String coinId) {
        User user = userService.findUserByJwtToken(jwtToken);
        Assets asset = assetRepository.findAssetsByUser_UserIdAndCoins_Id(user.getUserId(), coinId);
        return asset;
    }

    @Override
    public void deleteAsset(Long assetId) {
        assetRepository.deleteById(assetId);
    }
}
