package edu.miu.TradingPlatform.exception;

public class InValidOTPException extends RuntimeException {
  public InValidOTPException (String message) {
    super(message);
  }
}
