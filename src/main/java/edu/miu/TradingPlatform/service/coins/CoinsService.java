package edu.miu.TradingPlatform.service.coins;

import edu.miu.TradingPlatform.domain.Coins;
import edu.miu.TradingPlatform.dto.coins.response.CoinsResponseDTO;

import java.util.List;

public interface CoinsService {

  List<CoinsResponseDTO> getCoinList(int page) throws Exception;

  String getMarketChart(String coinId, int days);

  String getCoinsDetails(String coinId);

  Coins findCoinByCoinId(String coinId);

  String searchCoin(String keyword);

  String getTop50CoinsByMarketCapRank();

  String getTradingCoins();
}
