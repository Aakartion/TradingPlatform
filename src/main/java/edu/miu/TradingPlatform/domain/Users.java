package edu.miu.TradingPlatform.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import edu.miu.TradingPlatform.model.TwoFactorAuthentication;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tradingUsers")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;
    @Embedded
    private TwoFactorAuthentication twoFactorAuthentication = new TwoFactorAuthentication();
    private USER_ROLE userRole = USER_ROLE.CUSTOMER;   // By default customer
}
