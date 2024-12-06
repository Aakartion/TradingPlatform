package edu.miu.TradingPlatform.service.withdrawal;

import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.Withdrawal;

import java.util.List;

public interface WithdrawalService {
  Withdrawal requestWithdrawal(Long amount, User user);

  Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception;

  List<Withdrawal> getUsersWithdrawalHistory(User user);

  List<Withdrawal> getAllWithdrawalRequest();
}
