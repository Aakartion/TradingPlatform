package edu.miu.TradingPlatform.dto.request;


import edu.miu.TradingPlatform.model.TwoFactorAuthentication;

public record UserRequestDTO(
        String userFirstName,
        String userLastName,
        String userEmail,
        String userPassword,
        TwoFactorAuthentication twoFactorAuthentication
        ) {

        public UserRequestDTO(
                String userFirstName,
                String userLastName,
                String userEmail,
                String userPassword,
                TwoFactorAuthentication twoFactorAuthentication
        ){
                this.userFirstName = userFirstName;
                this.userLastName = userLastName;
                this.userEmail = userEmail;
                this.userPassword = userPassword;
                this.twoFactorAuthentication = twoFactorAuthentication != null ? twoFactorAuthentication : new TwoFactorAuthentication();
        }

        public static UserRequestDTO withDefaultTwoFactorAuth(
                String userFirstName,
                String userLastName,
                String userEmail,
                String userPassword
        ){
                return new UserRequestDTO(userFirstName, userLastName, userEmail, userPassword, new TwoFactorAuthentication());
        }

}