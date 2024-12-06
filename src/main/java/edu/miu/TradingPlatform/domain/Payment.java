package edu.miu.TradingPlatform.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;
    private Long amount;
    @Enumerated(EnumType.STRING)
    private PAYMENT_STATUS payment_order_status;
    @Enumerated(EnumType.STRING)
    private PAYMENT_METHOD payment_method;
    @ManyToOne
    private User user;
}
