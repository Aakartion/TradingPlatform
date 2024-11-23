package edu.miu.TradingPlatform.controller.secure;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.miu.TradingPlatform.domain.Coins;
import edu.miu.TradingPlatform.dto.coins.response.CoinsResponseDTO;
import edu.miu.TradingPlatform.service.coins.CoinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coins")
public class CoinController {

    private final CoinsService coinsService;
    private final ObjectMapper objectMapper;

    public CoinController(CoinsService coinsService, ObjectMapper objectMapper) {
        this.coinsService = coinsService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<CoinsResponseDTO>> getCoinsList(@RequestParam(value = "page", defaultValue = "1") int page) throws Exception {
        List<CoinsResponseDTO> coinsList = coinsService.getCoinList(page);
        return new ResponseEntity<>(coinsList, HttpStatus.OK);
    }

    @GetMapping("/{coinId}/market_chart")
    public ResponseEntity<JsonNode> getMarketChart(@PathVariable String coinId,
                                                   @RequestParam(value = "days", defaultValue = "7") int days) throws Exception {
        JsonNode jsonNode = coinsService.getMarketChart(coinId, days);
        return new ResponseEntity<>(jsonNode, HttpStatus.OK);
    }
}
