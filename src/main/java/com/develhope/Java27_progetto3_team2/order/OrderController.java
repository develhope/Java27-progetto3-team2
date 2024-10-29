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
    public ResponseEntity<OrderDTO> addNewOrder(@PathVariable Long userId, @PathVariable Long restaurantId, @RequestBody OrderDTO orderDTO) {
        OrderDTO newOrder = orderService.addNewOrder(userId, restaurantId, orderDTO);
        log.debug("Order added to the database: {}", newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }


    @PutMapping("/{idOrder}")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable("idOrder") Long idOrder) {
        OrderDTO orderDTOUpdated = orderService.updateOrder(orderDTO, idOrder);
        return ResponseEntity.ok(orderDTOUpdated);
    }

    @DeleteMapping("/{idOrder}")
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


    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orderDTOList = orderService.getAllOrder();
        log.debug("Fetched all orders");
        return ResponseEntity.status(HttpStatus.OK).body(orderDTOList);
    }
}
