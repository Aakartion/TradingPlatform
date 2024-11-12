package edu.miu.TradingPlatform.service;

import edu.miu.TradingPlatform.domain.Users;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    Users addUser(Users user);

    List<Users> getAllUsers();

    void removeUser(String username);

    String verifyUser(Users user);
}
