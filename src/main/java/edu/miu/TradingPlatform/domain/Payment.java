package edu.miu.TradingPlatform.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentOrderId;

    private Long amount;

    private PAYMENT_STATUS payment_order_status;

    private PAYMENT_METHOD payment_method;

    @ManyToOne
    private User user;
}
