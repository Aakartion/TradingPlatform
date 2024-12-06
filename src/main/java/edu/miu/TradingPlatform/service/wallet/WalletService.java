package edu.miu.TradingPlatform.service.wallet;

import edu.miu.TradingPlatform.domain.Order;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.Wallet;
import edu.miu.TradingPlatform.domain.WalletTransaction;
import edu.miu.TradingPlatform.exception.InsufficientBalanceException;

public interface WalletService {
  Wallet getUserWallet(String jwtToken);

  Wallet addBalance(Wallet wallet, Long balance);

  Wallet findWalletByWalletId(Long walletId);

    Wallet walletToWalletTransaction( String jwtToken, Long receiverWalletId, WalletTransaction walletTransaction) throws InsufficientBalanceException;

    Wallet payOrderPayment(String jwtToken, Long orderId) throws InsufficientBalanceException;
}
