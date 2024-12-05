package edu.miu.TradingPlatform.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    @ManyToOne    // One user has many order
    private User user;
    @Column(nullable = false)
    private ORDER_TYPE orderType;
    @Column(nullable = false)
    private BigDecimal orderPrice;
    private LocalDateTime orderTimeStamp = LocalDateTime.now();
    @Column(nullable = false)
    private ORDER_STATUS orderStatus;
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderItem orderItem;
}