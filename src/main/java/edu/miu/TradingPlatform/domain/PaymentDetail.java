package edu.miu.TradingPlatform.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentId;

    private String accountNumber;
    private String accountHolderName;
    private String routingNumber;
    private String bankName;
    @OneToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;
}
