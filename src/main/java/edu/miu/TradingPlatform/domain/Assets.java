package edu.miu.TradingPlatform.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Assets {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long assetId;
    private double assetQuantity;
    private double assetBuyPrice;
    @ManyToOne
    private Coins coins;
    @ManyToOne
    private User user;
}
