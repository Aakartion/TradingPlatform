package edu.miu.TradingPlatform.service.withdrawal.impl;

import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.domain.WITHDRAWAL_STATUS;
import edu.miu.TradingPlatform.domain.Withdrawal;
import edu.miu.TradingPlatform.exception.ResourcesNotFoundException;
import edu.miu.TradingPlatform.repository.WithdrawalRepository;
import edu.miu.TradingPlatform.service.withdrawal.WithdrawalService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {


    private final WithdrawalRepository withdrawalRepository;

    public WithdrawalServiceImpl(WithdrawalRepository withdrawalRepository) {
        this.withdrawalRepository = withdrawalRepository;
    }

    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setWithdrawalStatus(WITHDRAWAL_STATUS.PENDING);
        return withdrawalRepository.save(withdrawal);
    }

    @Override
    public Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception {
        Optional<Withdrawal> withdrawal = withdrawalRepository.findById(withdrawalId);
        if(withdrawal.isEmpty()){
      throw new ResourcesNotFoundException("Withdrawal: " + withdrawalId + " not found");
        }
        Withdrawal withdrawal1 = withdrawal.get();
        withdrawal1.setDateTime(LocalDateTime.now());
        if(accept){
            withdrawal1.setWithdrawalStatus(WITHDRAWAL_STATUS.SUCCESS);
        }else {
            withdrawal1.setWithdrawalStatus(WITHDRAWAL_STATUS.DECLINE);
        }
        return withdrawalRepository.save(withdrawal1);
    }

    @Override
    public List<Withdrawal> getUsersWithdrawalHistory(User user) {
        return withdrawalRepository.findByUser_UserId(user.getUserId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRepository.findAll();
    }
}
