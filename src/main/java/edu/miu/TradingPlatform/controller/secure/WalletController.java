package edu.miu.TradingPlatform.controller.secure;

import edu.miu.TradingPlatform.domain.Payment;
import edu.miu.TradingPlatform.domain.Wallet;
import edu.miu.TradingPlatform.domain.WalletTransaction;
import edu.miu.TradingPlatform.dto.payment.response.PaymentResponse;
import edu.miu.TradingPlatform.exception.InsufficientBalanceException;
import edu.miu.TradingPlatform.service.payment.PaymentService;
import edu.miu.TradingPlatform.service.wallet.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {
  private final WalletService walletService;
  private final PaymentService paymentService;

  public WalletController(WalletService walletService, PaymentService paymentService) {
    this.walletService = walletService;
    this.paymentService = paymentService;
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
      @RequestBody WalletTransaction walletTransaction)
      throws InsufficientBalanceException {
    Wallet wallet =
        walletService.walletToWalletTransaction(jwtToken, receiverWalletId, walletTransaction);
    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }

  @PutMapping("/order/{orderId}/pay")
  public ResponseEntity<Wallet> payOrderPayment(
      @RequestHeader("Authorization") String jwtToken, @PathVariable("orderId") Long orderId)
      throws InsufficientBalanceException {
    Wallet wallet = walletService.payOrderPayment(jwtToken, orderId);
    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }

  @PutMapping("/deposit")
  public ResponseEntity<Wallet> addMoneyToWallet(
      @RequestHeader("Authorization") String jwtToken,
      @RequestParam(name = "order_id") Long orderId,
      @RequestParam(name = "payment_id") String paymentId)
      throws Exception {
    Wallet wallet = walletService.getUserWallet(jwtToken);
    Payment payment = paymentService.getPaymentOrderById(orderId);

    Boolean status = paymentService.proccedPaymentOrder(payment, paymentId);
    if(wallet.getWalletBalance() == null){
      wallet.setWalletBalance(BigDecimal.valueOf(0));
    }
    if(status){
      wallet = walletService.addBalance(wallet, payment.getAmount());
    }

    PaymentResponse paymentResponse = new PaymentResponse();
    paymentResponse.setPayment_url("Deposit Success");
    return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
  }
}
