package edu.miu.TradingPlatform.service.impl;

import edu.miu.TradingPlatform.domain.Users;
import edu.miu.TradingPlatform.dto.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.response.UserResponseDTO;
import edu.miu.TradingPlatform.mapper.UsersMapper;
import edu.miu.TradingPlatform.repositorie.UserRepository;
import edu.miu.TradingPlatform.service.UserService;
import edu.miu.TradingPlatform.service.security_service.JwtService;
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

    public UserServiceImplementation(UserRepository userRepository,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager,
                                     UsersMapper usersMapper) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.usersMapper = usersMapper;
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
    public String verifyUser(Users user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserEmail(), user.getUserPassword()));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUserEmail());
        }
        return "failure";
    }
}
