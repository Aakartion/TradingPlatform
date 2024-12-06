package edu.miu.TradingPlatform.controller.secure;

import edu.miu.TradingPlatform.auth.response.AuthenticationResponseDTO;
import edu.miu.TradingPlatform.auth.response.ForgotPasswordApiResponse;
import edu.miu.TradingPlatform.domain.VerificationType;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.request.ForgotPasswordTokenRequest;
import edu.miu.TradingPlatform.dto.forgotPasswordToken.request.ResetPasswordRequestDTO;
import edu.miu.TradingPlatform.dto.users.response.UserResponseDTO;
import edu.miu.TradingPlatform.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/profile")
  public ResponseEntity<UserResponseDTO> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
    UserResponseDTO userResponseDTO = userService.getUserProfile(jwt);
    return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
  }

  @PostMapping("/verification/{verificationType}/send-otp")
  public ResponseEntity<UserResponseDTO> sendVerificationOtp (@RequestHeader("Authorization") String jwt,
                                                             @PathVariable VerificationType verificationType) throws Exception {
    UserResponseDTO userResponseDTO = userService.sendVerificationOtp(jwt, verificationType);
    return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
  }

  @PatchMapping("/enable-two-factor/verify-otp/{otp}")
  public ResponseEntity<UserResponseDTO> verifyTwoFactorAuthentication(@RequestHeader("Authorization")String jwtToken, @PathVariable String otp) throws Exception {
    UserResponseDTO userResponseDTO = userService.verifyTwoFactorAuthentication(jwtToken, otp);
    return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
  }

  @PostMapping("/reset-password/send-otp")
  public ResponseEntity<AuthenticationResponseDTO> sendForgotPasswordOtp (@RequestBody ForgotPasswordTokenRequest forgotPasswordTokenRequest) {
    System.out.println("This is forgot Password request" + forgotPasswordTokenRequest);
    AuthenticationResponseDTO authenticationResponseDTO = userService.sendForgotPasswordOtp(forgotPasswordTokenRequest);
    return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.OK);
  }

  @PatchMapping("/rest-password/verify-otp/{otp}")
  public ResponseEntity<ForgotPasswordApiResponse> resetPassword(@RequestParam String forgotPasswordTokenId, @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO, @RequestHeader("Authorization") String jwtToken) {
    System.out.println("This is jtwOken: " + jwtToken);
    ForgotPasswordApiResponse forgotPasswordApiResponse = userService.resetPassword(forgotPasswordTokenId, resetPasswordRequestDTO, jwtToken);
    return new ResponseEntity<>(forgotPasswordApiResponse, HttpStatus.OK);
  }
}
