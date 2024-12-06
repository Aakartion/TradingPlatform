package edu.miu.TradingPlatform.service.paymentDetails.impl;

import edu.miu.TradingPlatform.domain.PaymentDetail;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.repository.PaymentDetailRepository;
import edu.miu.TradingPlatform.service.paymentDetails.PaymentDetailService;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailServiceImpl implements PaymentDetailService {

    private final PaymentDetailRepository paymentDetailRepository;

    public PaymentDetailServiceImpl(PaymentDetailRepository paymentDetailRepository) {
        this.paymentDetailRepository = paymentDetailRepository;
    }

    @Override
    public PaymentDetail addPaymentDetail(String accountNumber, String accountHolderName, String routingNumber, String bankName, User user) {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setAccountNumber(accountNumber);
        paymentDetail.setAccountHolderName(accountHolderName);
        paymentDetail.setRoutingNumber(routingNumber);
        paymentDetail.setBankName(bankName);
        paymentDetail.setUser(user);
        return paymentDetailRepository.save(paymentDetail);
    }

    @Override
    public PaymentDetail getUserPaymentDetails(User user) {
        return paymentDetailRepository.findByUser_UserId(user.getUserId());
    }
}
