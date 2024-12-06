package edu.miu.TradingPlatform.repository;

import edu.miu.TradingPlatform.domain.PaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {
    PaymentDetail findByUser_UserId(long userId);
}
