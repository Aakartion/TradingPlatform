package edu.miu.TradingPlatform.controller;

import edu.miu.TradingPlatform.dto.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.response.UserLoginResponseDTO;
import edu.miu.TradingPlatform.dto.response.UserResponseDTO;
import edu.miu.TradingPlatform.service.TwoFactorOTPService;
import edu.miu.TradingPlatform.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    private final TwoFactorOTPService twoFactorOTPService;

    public AuthController(UserService userService,
                          TwoFactorOTPService twoFactorOTPService) {
        this.userService = userService;
        this.twoFactorOTPService = twoFactorOTPService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO){
        if (userRequestDTO.twoFactorAuthentication() == null) {
            userRequestDTO = UserRequestDTO.withDefaultTwoFactorAuth(
                    userRequestDTO.userFirstName(),
                    userRequestDTO.userLastName(),
                    userRequestDTO.userEmail(),
                    userRequestDTO.userPassword()
            );
        }
        UserResponseDTO savedUser = userService.addUser(userRequestDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    public ResponseEntity<UserLoginResponseDTO> verifySignInOTP(@PathVariable String otp,
                                                                @RequestParam String id) throws Exception {
        return new ResponseEntity<>(twoFactorOTPService.verifySignInOTP(otp,id), HttpStatus.OK);
    }
}
