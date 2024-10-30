package com.develhope.Java27_progetto3_team2.order;

import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    private Long id;
    private OrderStatus status;
    private String deliveryAddress;
    private String paymentMethod;
    private LocalDateTime orderDate;
    private Double totalPrice;
    private Long courierId;
    private Long userId;
    private Long restaurantId;
    private List<MenuItemDTO> items;  // Piatti del menù

}