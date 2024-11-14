package edu.miu.TradingPlatform.controller;

import edu.miu.TradingPlatform.dto.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.response.UserLoginResponseDTO;
import edu.miu.TradingPlatform.service.UserService;
import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody UserRequestDTO userRequestDTO) throws MessagingException {
        System.out.println("userRequestDTO.twoFactorAuthentication: " + userRequestDTO.twoFactorAuthentication());
        if (userRequestDTO.twoFactorAuthentication().isTwoFactorAuthenticationEnabled()) {
            System.out.println("*************It is NULL");
            userRequestDTO = UserRequestDTO.withDefaultTwoFactorAuth(
                    userRequestDTO.userFirstName(),
                    userRequestDTO.userLastName(),
                    userRequestDTO.userEmail(),
                    userRequestDTO.userPassword()
            );
        }
        System.out.println("It is ***not*** NULL "+ userRequestDTO.twoFactorAuthentication());
        return new ResponseEntity<>(userService.verifyUser(userRequestDTO), HttpStatus.OK);
    }
}
