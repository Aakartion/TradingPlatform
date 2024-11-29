package edu.miu.TradingPlatform.controller.unsecure;

import edu.miu.TradingPlatform.auth.request.AuthenticationRequestDTO;
import edu.miu.TradingPlatform.auth.request.RegisterRequestDTO;
import edu.miu.TradingPlatform.auth.response.AuthenticationResponseDTO;
import edu.miu.TradingPlatform.auth.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO){
        AuthenticationResponseDTO authenticationResponseDTO = authenticationService.register(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(authenticationResponseDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO authenticationRequestDTO){
        AuthenticationResponseDTO authenticationResponseDTO = authenticationService.authenticate(authenticationRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(authenticationResponseDTO);
    }
}
