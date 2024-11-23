package edu.miu.TradingPlatform.dto.coins.response;

public record ROI(
        Double times,
        String currency,
        Double percentage
) {
    public ROI {
        // Provide defaults if necessary
        times = (times == null) ? 0.0 : times;
        currency = (currency == null) ? "unknown" : currency;
        percentage = (percentage == null) ? 0.0 : percentage;
    }
}
