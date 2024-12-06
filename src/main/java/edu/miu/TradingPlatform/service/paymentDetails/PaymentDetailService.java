package edu.miu.TradingPlatform.service.paymentDetails;

import edu.miu.TradingPlatform.domain.PaymentDetail;
import edu.miu.TradingPlatform.domain.User;

public interface PaymentDetailService {
    PaymentDetail addPaymentDetail(String accountNumber,
                                   String accountHolderName,
                                   String routingNumber,
                                   String bankName,
                                   User user);

    PaymentDetail getUserPaymentDetails(User user);
}
