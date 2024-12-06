package edu.miu.TradingPlatform.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long withdrawalId;
    private WITHDRAWAL_STATUS withdrawalStatus;
    private Long amount;

    @ManyToOne
    private User user;

    private LocalDateTime dateTime = LocalDateTime.now();

}
