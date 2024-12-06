package edu.miu.TradingPlatform.controller.secure;


import edu.miu.TradingPlatform.domain.PaymentDetail;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.service.paymentDetails.PaymentDetailService;
import edu.miu.TradingPlatform.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment-details")
public class PaymentDetailsController {

    private final PaymentDetailService paymentDetailService;
    private final UserService userService;

    public PaymentDetailsController(PaymentDetailService paymentDetailService, UserService userService) {
        this.paymentDetailService = paymentDetailService;
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<PaymentDetail> addPaymentDetails(@RequestHeader("Authorization") String jwtToken,
                                                           @RequestBody PaymentDetail paymentDetailRequest) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        PaymentDetail paymentDetail = paymentDetailService.addPaymentDetail(paymentDetailRequest.getAccountNumber(),
                paymentDetailRequest.getAccountHolderName(),
                paymentDetailRequest.getRoutingNumber(),
                paymentDetailRequest.getBankName(),
                user);
        return new ResponseEntity<>(paymentDetail, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PaymentDetail> getUserPaymentDetails(@RequestHeader("Authorization") String jwtToken) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        PaymentDetail paymentDetail = paymentDetailService.getUserPaymentDetails(user);
        return new ResponseEntity<>(paymentDetail, HttpStatus.OK);
    }
}
