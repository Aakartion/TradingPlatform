package edu.miu.TradingPlatform.exception;


public class ResourceAlreadyPresentException extends RuntimeException {
    public ResourceAlreadyPresentException(String message) {
        super(message);
    }
}
