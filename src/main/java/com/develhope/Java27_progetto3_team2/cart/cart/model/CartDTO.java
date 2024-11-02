package com.develhope.Java27_progetto3_team2.cart.cart.model;

import com.develhope.Java27_progetto3_team2.cart.cartItem.model.CartItem;
import com.develhope.Java27_progetto3_team2.order.Order;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
    private Long id;
    private Status status;
    private List<Long> cartItemIdList;
    private Long userId;
    private Long orderId;
    private Long restaurantId;
}
