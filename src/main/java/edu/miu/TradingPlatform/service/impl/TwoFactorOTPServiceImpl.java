package edu.miu.TradingPlatform.service.impl;

import edu.miu.TradingPlatform.domain.Users;
import edu.miu.TradingPlatform.dto.response.UserLoginResponseDTO;
import edu.miu.TradingPlatform.model.TwoFactorOTP;
import edu.miu.TradingPlatform.repositorie.TwoFactorOTPRepository;
import edu.miu.TradingPlatform.service.TwoFactorOTPService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TwoFactorOTPServiceImpl implements TwoFactorOTPService {

    private final TwoFactorOTPRepository twoFactorOTPRepository;

    public TwoFactorOTPServiceImpl(TwoFactorOTPRepository twoFactorOTPRepository) {
        this.twoFactorOTPRepository = twoFactorOTPRepository;
    }

    @Override
    public TwoFactorOTP createTwoFactorOtp(Users user, String otp, String jwtToken) {
        UUID uuid = UUID.randomUUID();
        String twoFactorId = uuid.toString();

        TwoFactorOTP twoFactorOTP = new TwoFactorOTP();
        twoFactorOTP.setTwoFactorOTP(otp);
        twoFactorOTP.setJwtToken(jwtToken);
        twoFactorOTP.setTwoFactorOtpId(twoFactorId);
        twoFactorOTP.setUser(user);
        return twoFactorOTPRepository.save(twoFactorOTP);
    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return twoFactorOTPRepository.findByUser_UserId(userId);
//        return null;
    }

    @Override
    public TwoFactorOTP findById(String id) {
        Optional<TwoFactorOTP> searchTwoFactorOtp = twoFactorOTPRepository.findById(id);

        return searchTwoFactorOtp.orElse(null);
    }

    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOTP, String otp) {
        return twoFactorOTP.getTwoFactorOtpId().equals(otp);
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOTP) {
        twoFactorOTPRepository.delete(twoFactorOTP);
    }

    @Override
    public UserLoginResponseDTO verifySignInOTP(String otp, String id) throws Exception {
        Optional<TwoFactorOTP> twoFactorOTP = twoFactorOTPRepository.findById(id);
        if(twoFactorOTP.isPresent()){
            if(verifyTwoFactorOtp(twoFactorOTP.get(), otp)){
                UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO();
                userLoginResponseDTO.setMessage("Two factor authentication verified");
                userLoginResponseDTO.setTwoFactorAuthEnabled(true);
                userLoginResponseDTO.setJwtToken(twoFactorOTP.get().getJwtToken());
                return userLoginResponseDTO;
            }
        }

        throw new Exception("Invalid OTP");
    }
}
