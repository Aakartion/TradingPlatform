package edu.miu.TradingPlatform.repositorie;

import edu.miu.TradingPlatform.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
