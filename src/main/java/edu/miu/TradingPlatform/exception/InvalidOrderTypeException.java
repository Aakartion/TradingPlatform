package edu.miu.TradingPlatform.exception;

public class InvalidOrderTypeException extends RuntimeException {

  public InvalidOrderTypeException(String message) {
    super(message);
  }
}
