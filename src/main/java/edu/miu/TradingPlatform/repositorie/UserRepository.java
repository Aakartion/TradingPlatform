package edu.miu.TradingPlatform.repositorie;

import edu.miu.TradingPlatform.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findUserByUserEmail(String userEmail);
}
