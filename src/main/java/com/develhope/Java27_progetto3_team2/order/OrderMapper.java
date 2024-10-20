package com.develhope.Java27_progetto3_team2.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class OrderMapper {


    public Order mapperOrderDTOToOrder(OrderDTO orderDTO){
        return Order.builder().id(orderDTO.getId()).status(orderDTO.getStatus()).deliveryAddress(orderDTO.getDeliveryAddress()).paymentMethod(orderDTO.getPaymentMethod()).orderDate(orderDTO.getOrderDate()).totalPrice(orderDTO.getTotalPrice()).courierId(orderDTO.getCourierId()).build();
    }

    public OrderDTO mapperOrderToOrderDTO(Order order){
        return OrderDTO.builder().id(order.getId()).status(order.getStatus()).deliveryAddress(order.getDeliveryAddress()).paymentMethod(order.getPaymentMethod()).orderDate(order.getOrderDate()).totalPrice(order.getTotalPrice()).courierId(order.getCourierId()).build();
    }

    public List<OrderDTO> fromOrderListToDTOList(List<Order> orderList){
        return orderList.stream().map(this::mapperOrderToOrderDTO).toList();
    }
}
