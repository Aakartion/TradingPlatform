package edu.miu.TradingPlatform.controller.secure;

import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.Wallet;
import edu.miu.TradingPlatform.domain.Withdrawal;
import edu.miu.TradingPlatform.service.user.UserService;
import edu.miu.TradingPlatform.service.wallet.WalletService;
import edu.miu.TradingPlatform.service.withdrawal.WithdrawalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/withdrawal")
public class WithdrawalController {

  private final WithdrawalService withdrawalService;
  private final WalletService walletService;
  private final UserService userService;

  public WithdrawalController(
      WithdrawalService withdrawalService, WalletService walletService, UserService userService) {
    this.withdrawalService = withdrawalService;
    this.walletService = walletService;
    this.userService = userService;
  }

  @PostMapping("/{amount}")
  public ResponseEntity<?> withdrawalRequest(
      @PathVariable Long amount, @RequestHeader("Authorization") String jwtToken) throws Exception {
    User user = userService.findUserByJwtToken(jwtToken);
    Wallet userWallet = walletService.getUserWallet(jwtToken);
    Withdrawal withdrawal = withdrawalService.requestWithdrawal(amount, user);
    walletService.addBalance(userWallet, -withdrawal.getAmount());
    //        WalletTransaction walletTransaction = walletTransactionService.createTransaction(
    //                userWallet,
    //                WALLET_TRANSACTION_TYPE.WITHDRAWAL,
    //                null,
    //                "bank account withdrawal",
    //                withdrawal.getAmount()
    //        );
      return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  @PatchMapping("/admin/{withdrawalId}/proceed/{accept}")
  public ResponseEntity<?> proceedWithdrawal(
      @PathVariable Long withdrawalId,
      @PathVariable boolean accept,
      @RequestHeader("Authorization") String jwtToken)
      throws Exception {
    Withdrawal withdrawal = withdrawalService.proceedWithWithdrawal(withdrawalId, accept);
    Wallet userWallet = walletService.getUserWallet(jwtToken);
    // If admin doesn't accept the withdrawal then amount should be added back to the user
    if (!accept) {
      walletService.addBalance(userWallet, withdrawal.getAmount());
    }
    return new ResponseEntity<>(withdrawal, HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(
      @RequestHeader("Authorization") String jwtToken) throws Exception {
    User user = userService.findUserByJwtToken(jwtToken);
    List<Withdrawal> withdrawals = withdrawalService.getUsersWithdrawalHistory(user);
    return new ResponseEntity<>(withdrawals, HttpStatus.OK);
  }

  @GetMapping("/admin")
  public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(
      @RequestHeader("Authorization") String jwtToken) throws Exception {
    User user = userService.findUserByJwtToken(jwtToken);
    List<Withdrawal> withdrawals = withdrawalService.getAllWithdrawalRequest();
    return new ResponseEntity<>(withdrawals, HttpStatus.OK);
  }
}
