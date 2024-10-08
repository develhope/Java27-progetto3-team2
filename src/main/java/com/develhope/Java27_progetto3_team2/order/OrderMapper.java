package com.develhope.Java27_progetto3_team2.order;

import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public Order mapperOrderDTOToOrder(OrderDTO orderDTO){
        return Order.builder().id(orderDTO.getId()).status(orderDTO.getStatus()).deliveryAddress(orderDTO.getDeliveryAddress()).paymentMethod(orderDTO.getPaymentMethod()).orderDate(orderDTO.getOrderDate()).totalPrice(orderDTO.getTotalPrice()).courierId(orderDTO.getCourierId()).build();
    }

    public OrderDTO mapperOrderToOrderDTO(Order order){
        return OrderDTO.builder().id(order.getId()).status(order.getStatus()).deliveryAddress(order.getDeliveryAddress()).paymentMethod(order.getPaymentMethod()).orderDate(order.getOrderDate()).totalPrice(order.getTotalPrice()).courierId(order.getCourierId()).build();
    }
}
