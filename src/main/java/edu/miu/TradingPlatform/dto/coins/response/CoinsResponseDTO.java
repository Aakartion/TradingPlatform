package edu.miu.TradingPlatform.dto.coins.response;

import java.time.LocalDateTime;

public record CoinsResponseDTO(
    String symbol,
    String name,
    String image,
    Double current_price,
    Long market_cap,
    Integer market_cap_rank,
    Long fully_diluted_valuation,
    Long total_volume,
    Double high_24h,
    Double low_24h,
    Double price_change_24h,
    Double price_change_percentage_24h,
    Long market_cap_change_24h,
    Double market_cap_change_percentage_24h,
    Double circulating_supply,
    Double total_supply,
    Double max_supply,
    Double ath,
    Double ath_change_percentage,
    LocalDateTime ath_date,
    Double atl,
    Double atl_change_percentage,
    LocalDateTime atl_date,
    ROI roi,
    LocalDateTime last_updated) {}
