package com.develhope.Java27_progetto3_team2.cart.service;

import com.develhope.Java27_progetto3_team2.cart.cart.model.Cart;
import com.develhope.Java27_progetto3_team2.cart.cart.repository.CartRepository;
import com.develhope.Java27_progetto3_team2.cart.cartItem.model.CartItem;
import com.develhope.Java27_progetto3_team2.cart.cartItem.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceMapper {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart getCartById(Long cartId) {
        return cartRepository.getReferenceById(cartId);
    }

    public List<Long> getCartItemListIdfromCartItemList(List<CartItem> cartItemList) {
        List<Long> cartItemListId = new ArrayList<>();
        cartItemList.forEach(a -> cartItemListId.add(a.getId()));
        return cartItemListId;
    }

    public List<CartItem> getCartItemListfromCartItemIdList(List<Long> cartItemIdList) {
        List<CartItem> cartItemList = new ArrayList<>();
        cartItemIdList.forEach(a->cartItemList.add(cartItemRepository.getReferenceById(a)));
        return cartItemList;
    }
}
