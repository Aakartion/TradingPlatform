package edu.miu.TradingPlatform.dto.order.request;

import edu.miu.TradingPlatform.domain.ORDER_STATUS;

public record OrderRequestDTO(
         String coinId,
         double quantity,
         ORDER_STATUS orderType
) {}
