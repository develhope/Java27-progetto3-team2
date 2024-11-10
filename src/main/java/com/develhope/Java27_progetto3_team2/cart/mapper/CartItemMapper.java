package com.develhope.Java27_progetto3_team2.cart.mapper;

import com.develhope.Java27_progetto3_team2.cart.cartItem.model.CartItem;
import com.develhope.Java27_progetto3_team2.cart.cartItem.model.CartItemDTO;
import com.develhope.Java27_progetto3_team2.cart.service.CartServiceMapper;
import com.develhope.Java27_progetto3_team2.menu.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemMapper {
    private final MenuItemService menuItemService;
    private final CartServiceMapper cartService;

    public CartItemDTO cartItemToCartItemDTO(CartItem cartItem){
        return CartItemDTO.builder().id(cartItem.getId()).menuItemId(cartItem.getMenuItem().getId()).quantity(cartItem.getQuantity()).build();
    }
    public CartItem cartItemDTOToCartItem(CartItemDTO cartItemDTO){
        return CartItem.builder().id(cartItemDTO.getId()).menuItem(menuItemService.getMenuItemById(cartItemDTO.getMenuItemId())).quantity(cartItemDTO.getQuantity()).build();
    }

}
