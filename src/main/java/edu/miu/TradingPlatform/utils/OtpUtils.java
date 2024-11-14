package edu.miu.TradingPlatform.utils;

import java.util.Random;

public class OtpUtils {

    public static String generateOTP(){
        int otpLength = 6;
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for(int i=0; i<otpLength;i++){
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
