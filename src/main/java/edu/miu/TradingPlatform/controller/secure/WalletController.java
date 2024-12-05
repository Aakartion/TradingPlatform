package edu.miu.TradingPlatform.controller.secure;

import edu.miu.TradingPlatform.domain.Wallet;
import edu.miu.TradingPlatform.domain.WalletTransaction;
import edu.miu.TradingPlatform.exception.InsufficientBalanceException;
import edu.miu.TradingPlatform.service.wallet.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {
  private final WalletService walletService;

  public WalletController(WalletService walletService) {
    this.walletService = walletService;
  }

  @GetMapping
  public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwtToken) {
    Wallet wallet = walletService.getUserWallet(jwtToken);
    return new ResponseEntity<>(wallet, HttpStatus.OK);
  }

  @PutMapping("/{receiverWalletId}/transfer")
  public ResponseEntity<Wallet> walletToWalletTransfer(
      @RequestHeader("Authorization") String jwtToken,
      @PathVariable("receiverWalletId") Long receiverWalletId,
      @RequestBody WalletTransaction walletTransaction) throws InsufficientBalanceException {
    Wallet wallet =
        walletService.walletToWalletTransaction(jwtToken, receiverWalletId, walletTransaction);
    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }

  @PutMapping("/order/{orderId}/pay")
  public ResponseEntity<Wallet> payOrderPayment(
      @RequestHeader("Authorization") String jwtToken, @PathVariable("orderId") Long orderId) throws InsufficientBalanceException {
    Wallet wallet = walletService.payOrderPayment(jwtToken, orderId);
    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }
}
