package edu.miu.TradingPlatform.service.coins;

import com.fasterxml.jackson.databind.JsonNode;
import edu.miu.TradingPlatform.domain.Coins;
import edu.miu.TradingPlatform.dto.coins.response.CoinsResponseDTO;

import java.util.List;

public interface CoinsService {

  List<CoinsResponseDTO> getCoinList(int page) throws Exception;

  JsonNode getMarketChart(String coinId, int days) throws Exception;

  JsonNode getCoinsDetails(String coinId) throws Exception;

  Coins findCoinByCoinId(String coinId) throws Exception;

  JsonNode searchCoin(String keyword) throws Exception;

  String getTop50CoinsByMarketCapRank();

  String getTradingCoins();
}
