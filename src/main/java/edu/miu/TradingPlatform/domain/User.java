package edu.miu.TradingPlatform.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
public class User {
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
