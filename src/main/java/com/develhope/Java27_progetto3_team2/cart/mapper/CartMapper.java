package com.develhope.Java27_progetto3_team2.cart.mapper;

import com.develhope.Java27_progetto3_team2.cart.cart.model.Cart;
import com.develhope.Java27_progetto3_team2.cart.cart.model.CartDTO;
import com.develhope.Java27_progetto3_team2.cart.service.CartService;
import com.develhope.Java27_progetto3_team2.cart.service.CartServiceMapper;
import com.develhope.Java27_progetto3_team2.order.OrderService;
import com.develhope.Java27_progetto3_team2.restaurant.service.RestaurantService;
import com.develhope.Java27_progetto3_team2.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartMapper {
    private final CartServiceMapper cartService;
    private final RestaurantService restaurantService;
    private final OrderService orderService;
    private final UserService userService;

    public CartDTO cartToCartDTO(Cart cart){
        return CartDTO.builder().id(cart.getId()).cartItemIdList(cartService.getCartItemListIdfromCartItemList(cart.getCartItemList())).restaurantId(cart.getRestaurant().getId()).status(cart.getStatus()).orderId(cart.getOrder().getId()).userId(cart.getUser().getId()).build();
    }
    public Cart cartDTOToCart(CartDTO cartDTO){
        return Cart.builder().id(cartDTO.getId()).cartItemList(cartService.getCartItemListfromCartItemIdList(cartDTO.getCartItemIdList())).restaurant(restaurantService.getRestaurantById(cartDTO.getRestaurantId())).status(cartDTO.getStatus()).order(orderService.getOrderById(cartDTO.getOrderId())).user(userService.getUserById(cartDTO.getUserId())).build();
    }
}
