package com.develhope.Java27_progetto3_team2.order.controller;

import com.develhope.Java27_progetto3_team2.cart.cart.model.Cart;
import com.develhope.Java27_progetto3_team2.cart.service.CartService;
import com.develhope.Java27_progetto3_team2.exception.exceptions.EntityNotFoundException;
import com.develhope.Java27_progetto3_team2.order.dto.OrderDTO;
import com.develhope.Java27_progetto3_team2.order.dto.UserOrderDTO;
import com.develhope.Java27_progetto3_team2.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @PostMapping("/{userId}/{restaurantId}")
    public ResponseEntity<OrderDTO> addNewOrder(@PathVariable Long userId, @PathVariable Long restaurantId, @RequestBody OrderDTO orderDTO) {
        OrderDTO newOrder = orderService.addNewOrder(userId, restaurantId, orderDTO);
        log.debug("Order added to the database: {}", newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }


    @PutMapping("/user/{idOrder}")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable("idOrder") Long idOrder) {
        OrderDTO orderDTOUpdated = orderService.updateOrder(orderDTO, idOrder);
        return ResponseEntity.ok(orderDTOUpdated);
    }

    @DeleteMapping("/user/delete/{idOrder}")
    public ResponseEntity<String> deleteOrder(@PathVariable("idOrder") Long idOrder) {
        boolean isDeleted = orderService.deleteOrder(idOrder);
        if (isDeleted) {
            return ResponseEntity.ok("Order with ID " + idOrder + " has been deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + idOrder + " not found.");
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Long userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }


    @GetMapping("/courier/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orderDTOList = orderService.getAllOrder();
        log.debug("Fetched all orders");
        return ResponseEntity.status(HttpStatus.OK).body(orderDTOList);
    }

    @PostMapping("/user/cart-to-order")
    public ResponseEntity<UserOrderDTO> createOrderFromUserCart(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String address, @RequestParam String pay) {
        Cart cart = cartService.getUserCart(userDetails);
        if (cart == null) {
            throw new EntityNotFoundException("No open cart found for the user.");
        }
        UserOrderDTO userOrderDTO = orderService.addNewOrderFromCart(cart, address, pay);
        log.debug("Order created from user's cart: {}", userOrderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userOrderDTO);
    }

    @PatchMapping("/admin/order/status/{orderId}")
    public ResponseEntity<OrderDTO> changeOrderStatus(@PathVariable("orderId") Long orderId,String status){
        OrderDTO orderDTO = orderService.changeOrderStatus(orderId,status);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }

    @GetMapping("/user/order/details/{orderId}")
    public ResponseEntity<UserOrderDTO> getUserOrderDetails(@PathVariable("orderId") Long orderId){
        UserOrderDTO userOrderDTO = orderService.getUserOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(userOrderDTO);
    }
}
