package com.develhope.Java27_progetto3_team2.controller;

import com.develhope.Java27_progetto3_team2.model.OrderDTO;
import com.develhope.Java27_progetto3_team2.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;

    @PutMapping("/{idOrder}")
    public ResponseEntity<?> updateOrder(@RequestBody OrderDTO orderDTO, @PathVariable("idOrder") Long idOrder) throws Exception {
        try {
            OrderDTO orderDTOUpdated = orderService.updateOrder(orderDTO, idOrder);
            if (orderDTOUpdated == null) {
                return ResponseEntity.badRequest().body("Order did not updated!");
            }
            return ResponseEntity.ok(orderDTOUpdated);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idOrder}")
    public ResponseEntity<?> deleteOrder(@PathVariable("idOrder") Long idOrder) {
        try {
            boolean isDeleted = orderService.deleteOrder(idOrder);
            return ResponseEntity.ok("Order with id " + idOrder + " has been deleted.");
            //return ResponseEntity.noContent().build(); <-- codice 204 No content, delete idempotente
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while deleting the order: " + e.getMessage());
        }
    }

}
