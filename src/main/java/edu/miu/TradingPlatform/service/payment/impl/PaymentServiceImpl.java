package edu.miu.TradingPlatform.service.payment.impl;

import edu.miu.TradingPlatform.domain.PAYMENT_METHOD;
import edu.miu.TradingPlatform.domain.PAYMENT_STATUS;
import edu.miu.TradingPlatform.domain.Payment;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.dto.payment.response.PaymentResponse;
import edu.miu.TradingPlatform.exception.ResourcesNotFoundException;
import edu.miu.TradingPlatform.repository.PaymentRepository;
import edu.miu.TradingPlatform.service.payment.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment createPaymentOrder(User user, Long amount, PAYMENT_METHOD payment_method) {

        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPayment_method(payment_method);
        payment.setAmount(amount);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentOrderById(Long paymentOrderId) throws Exception {
    return paymentRepository
        .findById(paymentOrderId)
        .orElseThrow(() -> new ResourcesNotFoundException("Payment: "+ paymentOrderId + " not found"));
    }

    @Override
    public Boolean proccedPaymentOrder(Payment payment, String paymentId) {
        if(payment.getPayment_order_status().equals(PAYMENT_STATUS.PENDING)){
            if(payment.getPayment_method().equals(PAYMENT_METHOD.DIRECT_FROM_BANK)){
//                Some logic for payment
                payment.setPayment_order_status(PAYMENT_STATUS.SUCCESS);
                paymentRepository.save(payment);
            }
            if(payment.getPayment_method().equals(PAYMENT_METHOD.THIRD_PARTY)){
//                Some logic for payment
                payment.setPayment_order_status(PAYMENT_STATUS.SUCCESS);
                paymentRepository.save(payment);
            }
            return true;
        }
        return false;
    }

    @Override
    public PaymentResponse createDirectPaymentLink(User user, Long amount, Long orderId) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPayment_url("Payment Link for Direct Payment is created");
        return paymentResponse;
    }

    @Override
    public PaymentResponse createThirdPartyPaymentLink(User user, Long amount, Long orderId) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setPayment_url("Payment Link for Third party Payment is created");
        return paymentResponse;
    }
}
