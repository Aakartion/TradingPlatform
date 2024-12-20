package edu.miu.TradingPlatform.repository;

import edu.miu.TradingPlatform.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserEmail(String userEmail);

//    Optional<User> findUserByJwtToken(String jwtToken);
}
