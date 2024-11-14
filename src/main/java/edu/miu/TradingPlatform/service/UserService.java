package edu.miu.TradingPlatform.service;

import edu.miu.TradingPlatform.domain.Users;
import edu.miu.TradingPlatform.dto.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.response.UserLoginResponseDTO;
import edu.miu.TradingPlatform.dto.response.UserResponseDTO;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    UserResponseDTO addUser(UserRequestDTO userRequestDTO);

    List<Users> getAllUsers();

    void removeUser(String username);

    UserLoginResponseDTO verifyUser(UserRequestDTO userRequestDTO) throws MessagingException;
}
