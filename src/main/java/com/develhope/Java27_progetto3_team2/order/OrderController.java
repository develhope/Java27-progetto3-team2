package com.develhope.Java27_progetto3_team2.order;

import com.develhope.Java27_progetto3_team2.cart.cart.model.Cart;
import com.develhope.Java27_progetto3_team2.cart.cart.model.CartDTO;
import com.develhope.Java27_progetto3_team2.cart.mapper.CartMapper;
import com.develhope.Java27_progetto3_team2.cart.service.CartService;
import com.develhope.Java27_progetto3_team2.exception.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    CartService cartService;
    @Autowired
    CartMapper cartMapper;


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
    public ResponseEntity<OrderDTO> createOrderFromUserCart(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String address, @RequestParam String pay) {
        // Recupero il cart dell'utente autenticato, tramite metodo getUserCart
        CartDTO cartDTO = cartService.getUserCart(userDetails);
        if (cartDTO == null) {
            throw new EntityNotFoundException("No open cart found for the user.");
        }

        Cart cart = cartMapper.cartDTOToCart(cartDTO);
        // Creo ordine da carrello
        OrderDTO orderDTO = orderService.addNewOrderFromCart(cart, address, pay);

        log.debug("Order created from user's cart: {}", orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @PatchMapping("/admin/order/status/{orderId}")
    public ResponseEntity<OrderDTO> changeOrderStatus(@PathVariable("orderId") Long orderId,String status){
        OrderDTO orderDTO = orderService.changeOrderStatus(orderId,status);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }

}
