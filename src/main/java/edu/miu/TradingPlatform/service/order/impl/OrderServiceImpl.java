package edu.miu.TradingPlatform.service.order.impl;

import edu.miu.TradingPlatform.domain.*;
import edu.miu.TradingPlatform.dto.order.request.OrderRequestDTO;
import edu.miu.TradingPlatform.exception.InvalidOrderTypeException;
import edu.miu.TradingPlatform.exception.ResourcesNotFoundException;
import edu.miu.TradingPlatform.repository.OrderItemRepository;
import edu.miu.TradingPlatform.repository.OrderRepository;
import edu.miu.TradingPlatform.service.asset.AssetService;
import edu.miu.TradingPlatform.service.coins.CoinsService;
import edu.miu.TradingPlatform.service.order.OrderService;
import edu.miu.TradingPlatform.service.user.UserService;
import edu.miu.TradingPlatform.service.wallet.WalletService;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final UserService userService;
  private final CoinsService coinsService;
  private final AssetService assetService;
  private final WalletService walletService;

  public OrderServiceImpl(
      OrderRepository orderRepository,
      OrderItemRepository orderItemRepository,
      UserService userService,
      CoinsService coinsService,
      AssetService assetService,
      @Lazy WalletService walletService) {
    this.orderRepository = orderRepository;
    this.orderItemRepository = orderItemRepository;
    this.userService = userService;
    this.coinsService = coinsService;
    this.assetService = assetService;
    this.walletService = walletService;
  }

  @Override
  public Order createOrder(User user, OrderItem orderItem, ORDER_TYPE orderType) {
    double price = orderItem.getCoin().getCurrentPrice();
    Order order = new Order();
    order.setUser(user);
    order.setOrderType(orderType);
    order.setOrderPrice(BigDecimal.valueOf(price));
    order.setOrderItem(orderItem);
    order.setOrderTimeStamp(LocalDateTime.now());
    order.setOrderStatus(ORDER_STATUS.PENDING);
    return orderRepository.save(order);
  }

  @Override
  public Order getOrderByOrderId(Long orderId, String jwtToken) throws Exception {
    User user = userService.findUserByJwtToken(jwtToken);
    Order foundOrder = findOrderByOrderId(orderId);
    if (!foundOrder.getUser().getUserId().equals(user.getUserId())) {
      throw new AccessDeniedException("Order access denied");
    }
    return foundOrder;
  }

  @Override
  public Order findOrderByOrderId(Long orderId) {
    return orderRepository
        .findById(orderId)
        .orElseThrow(() -> new ResourcesNotFoundException("Order: " + orderId + " not found"));
  }

  @Override
  public List<Order> getAllOrdersOfUser(String jwtToken, ORDER_TYPE orderType, String assetSymbol) {

    Long userId = userService.findUserByJwtToken(jwtToken).getUserId();
    return orderRepository.findOrdersByUser_UserId(userId);
  }

  @Override
  public Order payOrderPayment(OrderRequestDTO orderRequestDTO, String jwtToken) throws Exception {
    User user = userService.findUserByJwtToken(jwtToken);
    System.out.println("Here is the user: " + user);
    Coins coin = coinsService.findCoinByCoinId(orderRequestDTO.coinId());
    if (orderRequestDTO.orderType().equals(ORDER_TYPE.BUY)) {
      return buyAsset(jwtToken, coin, orderRequestDTO.quantity(), user);
    } else if (orderRequestDTO.orderType().equals(ORDER_TYPE.SELL)) {
      return sellAsset(jwtToken, coin, orderRequestDTO.quantity(), user);
    }
    throw new InvalidOrderTypeException("Invalid Order Type");
  }

  @Transactional
  protected Order sellAsset(String jwtToken, Coins coin, double quantity, User user)
      throws Exception {
    if (quantity <= 0) {
      throw new InvalidOrderTypeException("Quantity should be > 0");
    }
    double sellPrice = coin.getCurrentPrice() * quantity;
    Assets assetToSell = assetService.findAssetByCoinId(jwtToken, coin.getId());
    double buyPrice = assetToSell.getAssetBuyPrice();

    if (assetToSell != null) {
      OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, sellPrice);
      Order order = createOrder(user, orderItem, ORDER_TYPE.SELL);
      orderItem.setOrder(order);

      if (assetToSell.getAssetQuantity() >= quantity) {
        order.setOrderStatus(ORDER_STATUS.SUCCESS);
        order.setOrderType(ORDER_TYPE.SELL);
        Order savedOrder = orderRepository.save(order);

        walletService.payOrderPayment(jwtToken, order.getOrderId());

        Assets updatedAsset = assetService.updateAsset(assetToSell.getAssetId(), -quantity);
        if (updatedAsset.getAssetQuantity() * coin.getCurrentPrice() <= 1) {
          assetService.deleteAsset(updatedAsset.getAssetId());
        }
        return savedOrder;
      }
      throw new Exception("Insufficient Quantity to sell");
    }
    throw new Exception("Asset not found");
  }

  private OrderItem createOrderItem(
      Coins coin, double quantity, double buyPrice, double sellPrice) {
    OrderItem orderItem = new OrderItem();
    orderItem.setCoin(coin);
    orderItem.setBuyPrice(buyPrice);
    orderItem.setSellPrice(sellPrice);
    orderItem.setQuantity(quantity);
    return orderItemRepository.save(orderItem);
  }

  @Transactional
  protected Order buyAsset(String jwtToken, Coins coin, double quantity, User user) throws Exception {
    if (quantity <= 0) {
      throw new InvalidOrderTypeException("Quantity should be > 0");
    }
    double buyPrice = coin.getCurrentPrice();
    OrderItem orderItem = createOrderItem(coin, quantity, buyPrice, 0);
    Order order = createOrder(user, orderItem, ORDER_TYPE.BUY);
    orderItem.setOrder(order);
    walletService.payOrderPayment(jwtToken, order.getOrderId());
    order.setOrderStatus(ORDER_STATUS.SUCCESS);
    order.setOrderType(ORDER_TYPE.BUY);
    Order savedOrder = orderRepository.save(order);

    Assets oldAsset =
        assetService.findAssetByCoinId(jwtToken, order.getOrderItem().getCoin().getId());
    if (oldAsset == null) {
      assetService.createAsset(user, orderItem.getCoin(), orderItem.getQuantity());
    } else {
      assetService.updateAsset(oldAsset.getAssetId(), quantity);
    }

    return savedOrder;
  }
}
