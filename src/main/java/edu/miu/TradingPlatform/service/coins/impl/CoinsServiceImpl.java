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
import java.util.Optional;

@Service
public class CoinsServiceImpl implements CoinsService {

  private final CoinsRepository coinsRepository;
  private final ObjectMapper objectMapper;

  private final WebClient webClient;

  public CoinsServiceImpl(
      CoinsRepository coinsRepository,
      ObjectMapper objectMapper) {
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
  public JsonNode getCoinsDetails(String coinId) throws Exception {
    //    https://api.coingecko.com/api/v3/coins/{bitcoin}
    try {
      JsonNode jsonNode =
          webClient
              .get()
              .uri(uriBuilder -> uriBuilder.path("/coins/{coinId}").build(coinId))
              .retrieve()
              .bodyToMono(JsonNode.class)
              .block();
      Coins coin = new Coins();
      coin.setId(jsonNode.get("id").asText());
      coin.setName(jsonNode.has("name") ? jsonNode.get("name").asText() : "Unknown");
      coin.setSymbol(jsonNode.has("symbol") ? jsonNode.get("symbol").asText() : "N/A");

      coin.setImage(
          jsonNode.has("image") && jsonNode.get("image").has("large")
              ? jsonNode.get("image").get("large").asText()
              : null);

      JsonNode marketData = jsonNode.get("market_data");
      if (marketData != null) {
        coin.setCurrentPrice(
            marketData.has("current_price") && marketData.get("current_price").has("usd")
                ? marketData.get("current_price").get("usd").asDouble()
                : 0.0);
        coin.setMarketCap(
            marketData.has("market_cap") && marketData.get("market_cap").has("usd")
                ? marketData.get("market_cap").get("usd").asLong()
                : 0L);
        coin.setMarketCapRank(
            marketData.has("market_cap_rank") ? marketData.get("market_cap_rank").asInt() : 0);
        coin.setTotalVolume(
            marketData.has("total_volume") && marketData.get("total_volume").has("usd")
                ? marketData.get("total_volume").get("usd").asLong()
                : 0L);
      }
      coinsRepository.save(coin);
      return jsonNode;

    } catch (Exception e) {
      throw new Exception("Error fetching coin details: " + e.getMessage());
    }
  }

  @Override
  public Coins findCoinByCoinId(String coinId) throws Exception {
    Optional<Coins> searchCoin = coinsRepository.findById(coinId);
    if(searchCoin.isPresent()){
      return searchCoin.get();
    }
    throw new Exception("Coin: " + coinId + " not found");
  }

  @Override
  public JsonNode searchCoin(String searchKeyword) throws Exception {
    //    "https://api.coingecko.com/api/v3/search?query=" + searchKeyword (it can be a word or
    // letter)
    try {

      return webClient
          .get()
          .uri(uriBuilder ->
                  uriBuilder
                          .path("/search")
                          .queryParam("query", searchKeyword)
                          .build())
              .retrieve()
              .bodyToMono(JsonNode.class)
              .block();
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public JsonNode getTop50CoinsByMarketCapRank() throws Exception {
//    https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=50&page=1;
    try{
      return webClient.get()
              .uri(uriBuilder ->
                      uriBuilder.path("/coins/markets")
                              .queryParam("vs_currency","usd")
                              .queryParam("per_page","50")
                              .queryParam("page","1")
                              .build())
              .retrieve()
              .bodyToMono(JsonNode.class)
              .block();
    }catch (Exception e){
      throw new Exception(e.getMessage());
    }
  }

  @Override
  public JsonNode getTrendingCoins() throws Exception {
    //     https://api.coingecko.com/api/v3/search/trending;
    try {
      return webClient
          .get()
          .uri(uriBuilder -> uriBuilder.path("/search/trending").build())
          .retrieve()
          .bodyToMono(JsonNode.class)
          .block();
    } catch (Exception e) {
      throw new Exception(e.getMessage());
    }
  }
}
