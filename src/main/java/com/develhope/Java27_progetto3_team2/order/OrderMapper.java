package com.develhope.Java27_progetto3_team2.order;

import com.develhope.Java27_progetto3_team2.menu.mapper.MenuItemMapper;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderMapper {

    private final MenuItemMapper menuItemMapper;

    public Order mapperOrderDTOToOrder(OrderDTO orderDTO){
        return Order.builder()
                .status(orderDTO.getStatus())
                .deliveryAddress(orderDTO.getDeliveryAddress())
                .paymentMethod(orderDTO.getPaymentMethod())
                .orderDate(orderDTO.getOrderDate())
                .totalPrice(orderDTO.getTotalPrice())
                .courierId(orderDTO.getCourierId())
                .items(orderDTO.getItems().stream()
                        .map(menuItemMapper::menuItemDTOToMenuItem)
                        .collect(Collectors.toList()))
                .build();
    }

    public OrderDTO mapperOrderToOrderDTO(Order order){
        return OrderDTO.builder()
                .id(order.getId())
                .status(order.getStatus())
                .deliveryAddress(order.getDeliveryAddress())
                .paymentMethod(order.getPaymentMethod())
                .orderDate(order.getOrderDate())
                .totalPrice(order.getTotalPrice())
                .courierId(order.getCourierId())
                .userId(order.getUser().getId())
                .restaurantId(order.getRestaurant().getId())
                .items(order.getItems().stream()
                        .map(menuItemMapper::menuItemToMenuItemDTO)
                        .collect(Collectors.toList()))
                .build();
    }

    public List<OrderDTO> fromOrderListToDTOList(List<Order> orderList){
        return orderList.stream().map(this::mapperOrderToOrderDTO).toList();
    }
}
