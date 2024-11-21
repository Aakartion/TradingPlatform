package edu.miu.TradingPlatform.service.coins.impl;

import edu.miu.TradingPlatform.domain.Coins;
import edu.miu.TradingPlatform.repository.CoinsRepository;
import edu.miu.TradingPlatform.service.coins.CoinsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoinsServiceImpl implements CoinsService {

    private final CoinsRepository coinsRepository;

    public CoinsServiceImpl(CoinsRepository coinsRepository) {
        this.coinsRepository = coinsRepository;
    }

    @Override
    public List<Coins> getCoinList(int page) {
        return List.of();
    }

    @Override
    public String getMarketChart(String coinId, int days) {
        return "";
    }

    @Override
    public String getCoinsDetails(String coinId) {
        return "";
    }

    @Override
    public Coins findCoinByCoinId(String coinId) {
        return null;
    }

    @Override
    public String searchCoin(String keyword) {
        return "";
    }

    @Override
    public String getTop50CoinsByMarketCapRank() {
        return "";
    }

    @Override
    public String getTradingCoins() {
        return "";
    }
}
