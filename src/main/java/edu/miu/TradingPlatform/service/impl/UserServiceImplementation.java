package edu.miu.TradingPlatform.service.impl;

import edu.miu.TradingPlatform.domain.Users;
import edu.miu.TradingPlatform.dto.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.response.UserLoginResponseDTO;
import edu.miu.TradingPlatform.dto.response.UserResponseDTO;
import edu.miu.TradingPlatform.mapper.UsersMapper;
import edu.miu.TradingPlatform.model.TwoFactorOTP;
import edu.miu.TradingPlatform.repositorie.UserRepository;
import edu.miu.TradingPlatform.service.EmailService;
import edu.miu.TradingPlatform.service.TwoFactorOTPService;
import edu.miu.TradingPlatform.service.UserService;
import edu.miu.TradingPlatform.service.security_service.JwtService;
import edu.miu.TradingPlatform.utils.OtpUtils;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    private final AuthenticationManager authenticationManager;

    private final UsersMapper usersMapper;

    private final TwoFactorOTPService twoFactorOTPService;

    private final EmailService emailService;

    public UserServiceImplementation(UserRepository userRepository,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager,
                                     UsersMapper usersMapper,
                                     TwoFactorOTPService twoFactorOTPService,
                                     EmailService emailService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.usersMapper = usersMapper;
        this.twoFactorOTPService = twoFactorOTPService;
        this.emailService = emailService;
    }

    @Override
    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {
        Users user = usersMapper.usersRequestDtoToUsers(userRequestDTO);
        System.out.println("This is usr: " + user.getUserLastName() + " " + user.getUserEmail());
        String encodedPassword = encoder.encode(user.getUserPassword());
        user.setUserPassword(encodedPassword);
        userRepository.save(user);
        UserResponseDTO userResponseDTO = usersMapper.usersToUserResponseDto(user);
        return userResponseDTO;
    }

    @Override
    public List<Users> getAllUsers() {
        return null;
    }

    @Override
    public void removeUser(String username) {

    }

    @Override
    public UserLoginResponseDTO verifyUser(UserRequestDTO userRequestDTO) throws MessagingException {
        Users user = usersMapper.usersRequestDtoToUsers(userRequestDTO);
        Authentication authentication = authenticateUser(user);
        String token = "";
        if(authentication.isAuthenticated()){
             token = jwtService.generateToken(user.getUserEmail());
        }
        return buildLoginResponse(userRequestDTO, token);
    }

    private Authentication authenticateUser(Users user) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPassword())
        );
    }


    private UserLoginResponseDTO buildLoginResponse(UserRequestDTO userRequestDTO, String token) throws MessagingException {

        Users authUser = userRepository.findUserByUserEmail(userRequestDTO.userEmail());

        if (userRequestDTO.twoFactorAuthentication().isTwoFactorAuthenticationEnabled()){
             UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
            userLoginResponseDTO.setMessage("Two factor authentication enabled");
            userLoginResponseDTO.setTwoFactorAuthEnabled(true);
            String otp = OtpUtils.generateOTP();
            TwoFactorOTP oldTwoFactorOtp = twoFactorOTPService.findByUser(authUser.getUserId());
            if(oldTwoFactorOtp!=null){
                twoFactorOTPService.deleteTwoFactorOtp(oldTwoFactorOtp);
            }

            TwoFactorOTP newTwoFactorOTP = twoFactorOTPService.createTwoFactorOtp(authUser, otp, token);
            emailService.sendVerificationOtpEmail(userRequestDTO.userEmail(), otp);
            userLoginResponseDTO.setSession(newTwoFactorOTP.getTwoFactorOtpId());
            return new UserLoginResponseDTO();
        }
        UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
        userLoginResponseDTO.setJwtToken(token);
        userLoginResponseDTO.setStatus(true);
        userLoginResponseDTO.setMessage("Login Success");
        return userLoginResponseDTO;
    }
}
