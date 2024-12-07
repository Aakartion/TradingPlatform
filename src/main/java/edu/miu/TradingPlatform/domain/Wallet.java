package edu.miu.TradingPlatform.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long walletId;
    @OneToOne
    private User user;
    private BigDecimal walletBalance = BigDecimal.ZERO;
}
