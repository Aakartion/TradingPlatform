package edu.miu.TradingPlatform.auth.service;

import edu.miu.TradingPlatform.auth.request.AuthenticationRequestDTO;
import edu.miu.TradingPlatform.auth.request.RegisterRequestDTO;
import edu.miu.TradingPlatform.auth.response.AuthenticationResponseDTO;
import edu.miu.TradingPlatform.config.authentication.JwtService;
import edu.miu.TradingPlatform.domain.TwoFactorOTP;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.exception.ResourceAlreadyPresentException;
import edu.miu.TradingPlatform.repository.UserRepository;
import edu.miu.TradingPlatform.utils.OtpGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository,
                                 AuthenticationManager authenticationManager,
                                 JwtService jwtService,
                                 PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationResponseDTO register(RegisterRequestDTO registerRequestDTO) throws Exception {

        Optional<User> existUser = userRepository.findByUserEmail(registerRequestDTO.userEmail());
        if(existUser.isPresent()){
            throw new ResourceAlreadyPresentException("Email is already used with another account");
        }
        User user = new User(
                registerRequestDTO.userFirstName(),
                registerRequestDTO.userLastName(),
                registerRequestDTO.userEmail(),
                passwordEncoder.encode(registerRequestDTO.userPassword()),
                null
        );

        User registeredUser = userRepository.save(user);

//        Generate the token
        String token = jwtService.generateToken(registeredUser);

        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
        authenticationResponseDTO.setJwtToken(token);
        authenticationResponseDTO.setStatus(true);
        authenticationResponseDTO.setMessage("User registered successfully");
        return authenticationResponseDTO;
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDTO.userEmail(),
                        authenticationRequestDTO.userPassword()
                )
        );
        User user = (User) authentication.getPrincipal();
//        User user = userRepository.findByUserEmail(authenticationRequestDTO.userEmail()).orElseThrow(()-> new UsernameNotFoundException("User not Found"));
        String token = jwtService.generateToken(user);

//    if (user.getTwoFactorAuthentication().isTwoFactorAuthenticationEnabled()){
//        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
//        authenticationResponseDTO.setJwtToken(token);
//        authenticationResponseDTO.setTwoFactorAuthenticationEnabled(true);
//        String otpCode = OtpGenerator.generateOtp();
//        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
//
//    }
        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO();
        authenticationResponseDTO.setJwtToken(token);
        authenticationResponseDTO.setStatus(true);
        authenticationResponseDTO.setMessage("User Login successfully");
      return authenticationResponseDTO;
    }

}
