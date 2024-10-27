package com.develhope.Java27_progetto3_team2.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;


    @PostMapping("/{userId}/{restaurantId}")
    public ResponseEntity<?> addNewOrder(@PathVariable Long userId, @PathVariable Long restaurantId, @RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO newOrder = orderService.addNewOrder(userId, restaurantId, orderDTO);
            log.debug("Order added to the database: {}", newOrder);
            return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
        } catch (Exception e) {
            log.error("Error adding new order: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating order: " + e.getMessage());
        }
    }

    @PutMapping("/{idOrder}")
    public ResponseEntity<?> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable("idOrder") Long idOrder) {
        try {
            OrderDTO orderDTOUpdated = orderService.updateOrder(orderDTO, idOrder);
            if (orderDTOUpdated == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order update failed!");
            }
            return ResponseEntity.ok(orderDTOUpdated);
        } catch (Exception e) {
            log.error("Error updating order with ID {}: {}", idOrder, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{idOrder}")
    public ResponseEntity<?> deleteOrder(@PathVariable("idOrder") Long idOrder) {
        try {
            boolean isDeleted = orderService.deleteOrder(idOrder);
            if (isDeleted) {
                return ResponseEntity.ok("Order with ID " + idOrder + " has been deleted.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with ID " + idOrder + " not found.");
            }
        } catch (Exception e) {
            log.error("Error deleting order with ID {}: {}", idOrder, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting order: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
            if (orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No orders found for user with ID " + userId);
            }
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("Error fetching orders for user with ID {}: {}", userId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching orders for user");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orderDTOList = orderService.getAllOrder();
        log.debug("Fetched all orders");
        return ResponseEntity.status(HttpStatus.OK).body(orderDTOList);
    }
}
