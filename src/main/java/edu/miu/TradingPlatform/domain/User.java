package edu.miu.TradingPlatform.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class User {
    private int userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
}
