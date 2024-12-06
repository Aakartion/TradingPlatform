package edu.miu.TradingPlatform.controller.secure;

import edu.miu.TradingPlatform.domain.Coins;
import edu.miu.TradingPlatform.domain.WatchList;
import edu.miu.TradingPlatform.service.watchlist.WatchListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/watchlist")
public class WatchlistController {

    private final WatchListService watchListService;

  public WatchlistController(WatchListService watchListService) {
    this.watchListService = watchListService;
  }

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchlist(@RequestHeader("Authorization") String jwtToken) throws Exception {
        WatchList watchList = watchListService.findUserWatchList(jwtToken);
        return new ResponseEntity<>(watchList, HttpStatus.OK);
    }

    @GetMapping("/{watchlistId}")
    public ResponseEntity<WatchList> getWatchlistById(@PathVariable Long watchlistId) throws Exception {
        WatchList watchList = watchListService.findByWatchListId(watchlistId);
        return new ResponseEntity<>(watchList, HttpStatus.OK);
    }

    @GetMapping("/add/coin/{coinId}")
    public ResponseEntity<Coins> getWatchlistByCoinId(@RequestHeader("Authorization") String jwtToken,
                                                  @PathVariable String coinId) throws Exception {
        Coins addedCoin = watchListService.getWatchlistByCoinId(jwtToken, coinId);
        return new ResponseEntity<>(addedCoin, HttpStatus.OK);
    }
}
