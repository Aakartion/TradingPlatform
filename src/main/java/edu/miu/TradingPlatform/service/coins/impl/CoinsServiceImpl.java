package edu.miu.TradingPlatform.service.coins.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.TradingPlatform.domain.Coins;
import edu.miu.TradingPlatform.dto.coins.response.CoinsResponseDTO;
import edu.miu.TradingPlatform.repository.CoinsRepository;
import edu.miu.TradingPlatform.service.coins.CoinsService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class CoinsServiceImpl implements CoinsService {

  private final CoinsRepository coinsRepository;
  private final ObjectMapper objectMapper;

  private final WebClient webClient;

  public CoinsServiceImpl(
      CoinsRepository coinsRepository,
      ObjectMapper objectMapper,
      WebClient webClient) {
    this.coinsRepository = coinsRepository;
    this.objectMapper = objectMapper;
    this.webClient = WebClient.builder().baseUrl("https://api.coingecko.com/api/v3").build();
  }

  @Override
  public List<CoinsResponseDTO> getCoinList(int page) throws Exception {
    try {
      //      https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page=1
      return webClient
          .get()
          .uri(
              uriBuilder ->
                  uriBuilder
                      .path("/coins/markets")
                      .queryParam("vs_currency", "usd")
                      .queryParam("per_page", "10")
                      .queryParam("page", page)
                      .build())
          .header(HttpHeaders.CONTENT_TYPE, "application/json")
          .retrieve()
          .bodyToFlux(CoinsResponseDTO.class)
          .collectList()
          .block();
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public JsonNode getMarketChart(String coinId, int days) throws Exception {
//    "https://api.coingecko.com/api/v3/coins/{bitcoin}/market_chart?vs_currency=usd&days=1"
    try{
      return webClient
          .get()
          .uri(
              uriBuilder ->
                  uriBuilder
                      .path("/coins/{coinId}/market_chart")
                      .queryParam("vs_currency", "usd")
                      .queryParam("days", days)
                      .build(coinId))
          .retrieve()
          .bodyToMono(JsonNode.class)
          .block();
    }catch (Exception e){
      throw new Exception(e.getMessage());
    }
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
