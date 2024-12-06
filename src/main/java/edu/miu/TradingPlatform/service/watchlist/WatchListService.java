package edu.miu.TradingPlatform.service.watchlist;

import edu.miu.TradingPlatform.domain.Coins;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.WatchList;

public interface WatchListService {

  WatchList findUserWatchList(String jwtToken) throws Exception;

  WatchList createWatchList(User user);

  WatchList findByWatchListId(Long watchListId) throws Exception;

  Coins getWatchlistByCoinId(String jwtToken, String coinId) throws Exception;
}
