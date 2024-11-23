package edu.miu.TradingPlatform.auth.service;

import edu.miu.TradingPlatform.auth.request.AuthenticationRequestDTO;
import edu.miu.TradingPlatform.auth.request.RegisterRequestDTO;
import edu.miu.TradingPlatform.auth.response.AuthenticationResponseDTO;
import edu.miu.TradingPlatform.config.authentication.JwtService;
import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public AuthenticationResponseDTO register(RegisterRequestDTO registerRequestDTO){
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

        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO(
                token,
                true,
                "Successfully Registered",
                false,
                "This is session"
        );
        return authenticationResponseDTO;
    }

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO authenticationRequestDTO) {
        System.out.println("****PPPKKK");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDTO.userEmail(),
                        authenticationRequestDTO.userPassword()
                )
        );
        System.out.println("#####");
//        User user = (User) authentication.getPrincipal();
        User user = userRepository.findByUserEmail(authenticationRequestDTO.userEmail()).orElseThrow(()-> new UsernameNotFoundException("User not Found"));
        System.out.println("This is user=> " + user);
        String token = jwtService.generateToken(user);
        System.out.println("Token: " + token);
        System.out.println("This is user email: " + user.getUserEmail());
        return new AuthenticationResponseDTO(token,true, "Hey hey", true, "Session session");
    }
}
