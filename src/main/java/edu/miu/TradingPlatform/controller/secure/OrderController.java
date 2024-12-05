package edu.miu.TradingPlatform.controller.secure;

import edu.miu.TradingPlatform.domain.ORDER_TYPE;
import edu.miu.TradingPlatform.domain.Order;
import edu.miu.TradingPlatform.dto.order.request.OrderRequestDTO;
import edu.miu.TradingPlatform.service.coins.CoinsService;
import edu.miu.TradingPlatform.service.order.OrderService;
import edu.miu.TradingPlatform.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final CoinsService coinsService;

    public OrderController(OrderService orderService, UserService userService, CoinsService coinsService) {
        this.orderService = orderService;
        this.userService = userService;
        this.coinsService = coinsService;
    }

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(@RequestBody OrderRequestDTO orderRequestDTO, @RequestHeader("Authorization") String jwtToken) throws Exception {
        Order order = orderService.payOrderPayment(orderRequestDTO, jwtToken);
        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderByOrderId(@RequestHeader("Authorization") String jwtToken,
                                              @PathVariable Long orderId) throws Exception {
        if(jwtToken == null){
            throw new Exception("Token is Missing");
        }
        Order order = orderService.getOrderByOrderId(orderId, jwtToken);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrdersForUser(@RequestHeader("Authorization") String jwtToken,
                                                                      @RequestParam(required = false) ORDER_TYPE order_type,
                                                                      @RequestParam(required = false) String asset_symbol) {
        List<Order> order = orderService.getAllOrdersOfUser(jwtToken, order_type, asset_symbol);
        return ResponseEntity.ok(order);
    }

}
