package com.develhope.Java27_progetto3_team2.order;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
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

}