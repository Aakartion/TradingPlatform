package edu.miu.TradingPlatform.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.miu.TradingPlatform.domain.Users;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class TwoFactorOTP {

    @Id
    private String twoFactorOtpId;
    private String twoFactorOTP;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    private Users user;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String jwtToken;
}
