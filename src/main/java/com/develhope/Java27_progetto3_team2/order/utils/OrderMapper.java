package com.develhope.Java27_progetto3_team2.order.utils;

import com.develhope.Java27_progetto3_team2.cart.mapper.CartMapper;
import com.develhope.Java27_progetto3_team2.menu.mapper.MenuItemMapper;
import com.develhope.Java27_progetto3_team2.order.dto.OrderDTO;
import com.develhope.Java27_progetto3_team2.order.dto.UserOrderDTO;
import com.develhope.Java27_progetto3_team2.order.entity.Order;
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
    private final CartMapper cartMapper;

    public Order mapperOrderDTOToOrder(OrderDTO orderDTO){
        return Order.builder()
                .status(orderDTO.getStatus())
                .deliveryAddress(orderDTO.getDeliveryAddress())
                .paymentMethod(orderDTO.getPaymentMethod())
                .orderDate(orderDTO.getOrderDate())
                .totalPrice(orderDTO.getTotalPrice())
                .courierId(orderDTO.getCourierId())
               .items(orderDTO.getItems().stream()
                         .map(item -> {
                             try {
                                 return menuItemMapper.menuItemDTOToMenuItem(item);
                             } catch (Exception e) {
                                 throw new RuntimeException("Error converting MenuItemDTO to MenuItem", e);
                             }
                         }).collect(Collectors.toList()))

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
                 .cart(order.getCart().getId())
                 .items(order.getItems().stream()
                         .map(menuItemMapper::menuItemToMenuItemDTO)
                         .collect(Collectors.toList()))

                .build();

    }

    public List<OrderDTO> fromOrderListToDTOList(List<Order> orderList){
        return orderList.stream().map(this::mapperOrderToOrderDTO).toList();
    }

    public UserOrderDTO toUserOrderDTO (Order order){
        UserOrderDTO userOrderDTO = new UserOrderDTO();
        userOrderDTO.setDeliveryAddress(order.getDeliveryAddress());
        userOrderDTO.setPaymentMethod(order.getPaymentMethod());
        userOrderDTO.setOrderDate(order.getOrderDate());
        userOrderDTO.setTotalPrice(order.getTotalPrice());
        userOrderDTO.setRestaurantName(order.getRestaurant().getNameRestaurant());
        userOrderDTO.setCartDTO(cartMapper.cartToCartDTO(order.getCart()));
        userOrderDTO.setDeliveryTime(order.getDeliveryTime());
        return userOrderDTO;

    }


}
