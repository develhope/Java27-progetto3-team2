package com.develhope.Java27_progetto3_team2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private String status;
    private String deliveryAddress;
    private String paymentMethod;
    private Date orderDate;
    private Double totalPrice;
    private Long courierId;

}