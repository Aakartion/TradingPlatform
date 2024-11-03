package edu.miu.TradingPlatform.controller;

import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.repositorie.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/get")
    public String AuthHome(){
        return "HEllo AUTh";
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> register(@RequestBody User user){
        User newUser = new User();
        newUser.setUserEmail(user.getUserEmail());
        newUser.setUserPassword(user.getUserPassword());
        newUser.setUserFirstName(user.getUserFirstName());
        newUser.setUserLastName(user.getUserLastName());

        User savedUser = userRepository.save(newUser);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
