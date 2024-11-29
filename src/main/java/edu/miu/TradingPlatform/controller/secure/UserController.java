package edu.miu.TradingPlatform.controller.secure;

import edu.miu.TradingPlatform.domain.VerificationType;
import edu.miu.TradingPlatform.dto.users.response.UserResponseDTO;
import edu.miu.TradingPlatform.service.EmailService;
import edu.miu.TradingPlatform.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;
  private final EmailService emailService;

  public UserController(UserService userService, EmailService emailService) {
    this.userService = userService;
    this.emailService = emailService;
  }

  @GetMapping("/profile")
  public ResponseEntity<UserResponseDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
    UserResponseDTO userResponseDTO = userService.findUserByJwtToken(jwt);
    return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
  }

  @PostMapping("/verification/{verificationType}/send-otp")
  public ResponseEntity<UserResponseDTO> sendVerificationOtp(@RequestHeader("Authorization") String jwt,
                                                             @PathVariable VerificationType verificationType) {

    return null;
  }


}
