package edu.miu.TradingPlatform.domain;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class WalletTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long walletTransactionId;
    @ManyToOne
    private Wallet wallet;
    @Enumerated(EnumType.STRING)
    private WALLET_TRANSACTION_TYPE walletTransactionType;
    private LocalDate walletTransactionDate;
    private String transferId;   // For Wallet to Wallet transfer
    private String walletTransactionPurpose;
    private Long amount;
}
