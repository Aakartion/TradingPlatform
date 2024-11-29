package edu.miu.TradingPlatform.controller.secure;

import edu.miu.TradingPlatform.dto.users.response.UserResponseDTO;
import edu.miu.TradingPlatform.service.EmailService;
import edu.miu.TradingPlatform.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;
  private final EmailService emailService;

  public UserController(UserService userService, EmailService emailService) {
    this.userService = userService;
    this.emailService = emailService;
  }


  @GetMapping
  public ResponseEntity<UserResponseDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
    UserResponseDTO userResponseDTO = userService.findUserByJwtToken(jwt);
    return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
  }
}
