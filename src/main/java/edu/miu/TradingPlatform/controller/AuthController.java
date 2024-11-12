package edu.miu.TradingPlatform.controller;

import edu.miu.TradingPlatform.domain.Users;
import edu.miu.TradingPlatform.dto.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.response.UserResponseDTO;
import edu.miu.TradingPlatform.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO userRequestDTO){
        UserResponseDTO savedUser = userService.addUser(userRequestDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
