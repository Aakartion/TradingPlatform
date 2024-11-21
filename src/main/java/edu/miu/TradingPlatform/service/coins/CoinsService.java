package edu.miu.TradingPlatform.service.coins;

import edu.miu.TradingPlatform.domain.Coins;

import java.util.List;

public interface CoinsService {

    List<Coins> getCoinList(int page);
}
