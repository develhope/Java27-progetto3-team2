package com.develhope.Java27_progetto3_team2.cart.cartItem.model;

import com.develhope.Java27_progetto3_team2.cart.cart.model.Cart;
import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDTO {
    private Long id;
    private Long cartId;
    private Long menuItemId;
    private int quantity;
}
