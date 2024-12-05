package edu.miu.TradingPlatform.service.wallet.impl;

import edu.miu.TradingPlatform.domain.*;
import edu.miu.TradingPlatform.exception.InsufficientBalanceException;
import edu.miu.TradingPlatform.exception.ResourcesNotFoundException;
import edu.miu.TradingPlatform.repository.WalletRepository;
import edu.miu.TradingPlatform.service.order.OrderService;
import edu.miu.TradingPlatform.service.user.UserService;
import edu.miu.TradingPlatform.service.wallet.WalletService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {

  private final WalletRepository walletRepository;
  private final UserService userService;
  private final OrderService orderService;

  public WalletServiceImpl(
      WalletRepository walletRepository, UserService userService, OrderService orderService) {
    this.walletRepository = walletRepository;
    this.userService = userService;
    this.orderService = orderService;
  }

  @Override
  public Wallet getUserWallet(String jwtToken) {
    User user = userService.findUserByJwtToken(jwtToken);
    Wallet wallet = walletRepository.findByUser_UserId(user.getUserId());
    if (wallet == null) {
      wallet = new Wallet();
      wallet.setUser(user);
      walletRepository.save(wallet);
    }
    return wallet;
  }

  @Override
  public Wallet addBalance(Wallet wallet, Double balance) {
    BigDecimal amount = wallet.getWalletBalance();
    BigDecimal newBalance = amount.add(BigDecimal.valueOf(balance));
    wallet.setWalletBalance(newBalance);
    return walletRepository.save(wallet);
  }

  @Override
  public Wallet findWalletByWalletId(Long walletId) {
    Optional<Wallet> wallet = walletRepository.findById(walletId);
    if (wallet.isPresent()) {
      return wallet.get();
    }
    throw new ResourcesNotFoundException("Wallet: " + walletId + " not found");
  }

  @Override
  public Wallet walletToWalletTransaction(
      String jwtToken, Long receiverWalletId, WalletTransaction walletTransaction)
      throws InsufficientBalanceException {
    Wallet senderWallet = getUserWallet(jwtToken);
    if (senderWallet.getWalletBalance().compareTo(BigDecimal.valueOf(walletTransaction.getAmount()))
        < 0) {
      throw new InsufficientBalanceException("Insufficient Balance");
    }

    Wallet receiverWallet = findWalletByWalletId(receiverWalletId);

    BigDecimal senderBalance =
        senderWallet.getWalletBalance().subtract(BigDecimal.valueOf(walletTransaction.getAmount()));
    senderWallet.setWalletBalance(senderBalance);
    walletRepository.save(senderWallet);

    BigDecimal receiverBalance =
        receiverWallet.getWalletBalance().add(BigDecimal.valueOf(walletTransaction.getAmount()));
    receiverWallet.setWalletBalance(receiverBalance);
    walletRepository.save(receiverWallet);
    return senderWallet;
  }

  @Override
  public Wallet payOrderPayment(String jwtToken, Long orderId) throws InsufficientBalanceException {
    Wallet wallet = getUserWallet(jwtToken);
    Order order = orderService.findOrderByOrderId(orderId);
    if (order.getOrderType().equals(ORDER_TYPE.BUY)) {
      BigDecimal newBalance = wallet.getWalletBalance().subtract(order.getOrderPrice());
      if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
        throw new InsufficientBalanceException("Insufficient fund fot this transaction");
      }
      wallet.setWalletBalance(newBalance);
    } else {
      BigDecimal newBalance = wallet.getWalletBalance().add(order.getOrderPrice());
      wallet.setWalletBalance(newBalance);
    }
    return walletRepository.save(wallet);
  }
}
