package edu.miu.TradingPlatform.controller;

import edu.miu.TradingPlatform.domain.Users;
import edu.miu.TradingPlatform.repositorie.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Users> register(@RequestBody Users user){
        Users newUser = new Users();
        newUser.setUserEmail(user.getUserEmail());
        newUser.setUserPassword(user.getUserPassword());
        newUser.setUserFirstName(user.getUserFirstName());
        newUser.setUserLastName(user.getUserLastName());

        Users savedUser = userRepository.save(newUser);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
