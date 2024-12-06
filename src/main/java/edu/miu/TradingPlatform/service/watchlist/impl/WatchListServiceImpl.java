package edu.miu.TradingPlatform.service.watchlist.impl;

import edu.miu.TradingPlatform.domain.Coins;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.WatchList;
import edu.miu.TradingPlatform.exception.ResourcesNotFoundException;
import edu.miu.TradingPlatform.repository.WatchListRepository;
import edu.miu.TradingPlatform.service.coins.CoinsService;
import edu.miu.TradingPlatform.service.user.UserService;
import edu.miu.TradingPlatform.service.watchlist.WatchListService;
import org.springframework.stereotype.Service;

@Service
public class WatchListServiceImpl implements WatchListService {
  private final UserService userService;
  private final CoinsService coinsService;
  private final WatchListRepository watchListRepository;

  public WatchListServiceImpl(
      UserService userService, CoinsService coinsService, WatchListRepository watchListRepository) {
    this.userService = userService;
    this.coinsService = coinsService;
    this.watchListRepository = watchListRepository;
  }

  @Override
  public WatchList findUserWatchList(String jwtToken) throws Exception {
    User user = userService.findUserByJwtToken(jwtToken);
    WatchList watchList = watchListRepository.findByUser_UserId(user.getUserId());
    if (watchList == null) {
      throw new ResourcesNotFoundException(
          "WatchList for user: " + user.getUserId() + " not found");
    }
    return watchList;
  }

  @Override
  public WatchList createWatchList(User user) {
    WatchList watchList = new WatchList();
    watchList.setUser(user);
    return watchListRepository.save(watchList);
  }

  @Override
  public WatchList findByWatchListId(Long watchListId) throws Exception {
    return watchListRepository
        .findById(watchListId)
        .orElseThrow(
            () -> new ResourcesNotFoundException("Watchlist: " + watchListId + " not found"));
  }

  @Override
  public Coins getWatchlistByCoinId(String jwtToken, String coinId) throws Exception {
    Coins coin = coinsService.findCoinByCoinId(coinId);
    WatchList watchList = findUserWatchList(jwtToken);

    if (watchList.getCoins().contains(coin)) {
      watchList.getCoins().remove(coin);
    } else {
      watchList.getCoins().add(coin);
      watchListRepository.save(watchList);
    }
    return coin;
  }
}
