package edu.miu.TradingPlatform.service.security_service;

import edu.miu.TradingPlatform.domain.Users;
import edu.miu.TradingPlatform.repositorie.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findUserByUserEmail(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
//        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
//
//        return new org.springframework.security.core.userdetails.User(user.getUserEmail()
//        ,user.getUserPassword(), grantedAuthorityList);

        return new CustomUserPrinciple(user);
    }
}
