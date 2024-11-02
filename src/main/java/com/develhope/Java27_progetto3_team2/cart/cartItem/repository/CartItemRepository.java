package com.develhope.Java27_progetto3_team2.cart.cartItem.repository;

import com.develhope.Java27_progetto3_team2.cart.cart.model.Cart;
import com.develhope.Java27_progetto3_team2.cart.cartItem.model.CartItem;
import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByMenuItemAndCart(MenuItem menuItem, Cart cart);
}
