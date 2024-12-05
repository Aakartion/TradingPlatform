package edu.miu.TradingPlatform.repository;

import edu.miu.TradingPlatform.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {}
