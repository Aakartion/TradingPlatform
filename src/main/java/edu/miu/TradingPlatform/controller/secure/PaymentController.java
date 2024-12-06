package edu.miu.TradingPlatform.controller.secure;

import edu.miu.TradingPlatform.domain.PAYMENT_METHOD;
import edu.miu.TradingPlatform.domain.Payment;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.dto.payment.response.PaymentResponse;
import edu.miu.TradingPlatform.service.payment.PaymentService;
import edu.miu.TradingPlatform.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;

    public PaymentController(PaymentService paymentService, UserService userService) {
        this.paymentService = paymentService;
        this.userService = userService;
    }

    @PostMapping("/{payment_method}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(@RequestHeader("Authorization") String jwtToken,
                                                          @PathVariable PAYMENT_METHOD payment_method,
                                                          @PathVariable Long amount) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        PaymentResponse paymentResponse = new PaymentResponse();
        Payment payment = paymentService.createPaymentOrder(user, amount, payment_method);

        if(payment_method.equals(PAYMENT_METHOD.DIRECT_FROM_BANK)){
            paymentResponse = paymentService.createDirectPaymentLink(user, amount, payment.getPaymentOrderId());
        }else {
            paymentResponse = paymentService.createThirdPartyPaymentLink(user, amount, payment.getPaymentOrderId());
        }

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }
}
