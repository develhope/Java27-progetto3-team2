package com.develhope.Java27_progetto3_team2.order.dto;

import com.develhope.Java27_progetto3_team2.cart.cart.model.CartDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOrderDTO {
    private String deliveryAddress;
    private String paymentMethod;
    private LocalDateTime orderDate;
    private Double totalPrice;
    private String restaurantName;
    private CartDTO cartDTO;
    private LocalDateTime deliveryTime;
}
