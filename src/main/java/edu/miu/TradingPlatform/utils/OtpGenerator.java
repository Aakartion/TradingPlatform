package edu.miu.TradingPlatform.utils;

import java.util.Random;

public class OtpGenerator {

  public static String generateOtp() {
    int optLength = 6;
    Random rand = new Random();
    StringBuilder otp = new StringBuilder();
    for (int i = 0; i < optLength; i++) {
      otp.append(rand.nextInt(10));
    }
    return otp.toString();
  }
}
