package edu.miu.TradingPlatform.repository;

import edu.miu.TradingPlatform.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
