package com.develhope.Java27_progetto3_team2.order.dto;

import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.order.enumerator.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long cart;
    private List<MenuItemDTO> items;
    private LocalDateTime deliveryTime;

}