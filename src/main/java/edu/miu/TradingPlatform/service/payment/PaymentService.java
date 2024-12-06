package edu.miu.TradingPlatform.service.payment;

import edu.miu.TradingPlatform.domain.PAYMENT_METHOD;
import edu.miu.TradingPlatform.domain.Payment;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.dto.payment.response.PaymentResponse;

public interface PaymentService {

    Payment createPaymentOrder(User user, Long amount, PAYMENT_METHOD payment_method);

    Payment getPaymentOrderById(Long paymentOrderId) throws Exception;

    Boolean proccedPaymentOrder(Payment payment, String paymentId);

    PaymentResponse createDirectPaymentLink(User user, Long amount, Long orderId);

    PaymentResponse createThirdPartyPaymentLink(User user, Long amount, Long orderId);
}
