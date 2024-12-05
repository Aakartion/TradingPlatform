package edu.miu.TradingPlatform.service.order;

import edu.miu.TradingPlatform.domain.*;
import edu.miu.TradingPlatform.dto.order.request.OrderRequestDTO;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, ORDER_TYPE orderType);

    Order getOrderByOrderId(Long orderId, String jwtToken) throws Exception;

    Order findOrderByOrderId(Long orderId);

    List<Order> getAllOrdersOfUser(String jwtToken, ORDER_TYPE orderType, String assetSymbol);

    Order payOrderPayment(OrderRequestDTO orderRequestDTO, String jwtToken) throws Exception;
}
