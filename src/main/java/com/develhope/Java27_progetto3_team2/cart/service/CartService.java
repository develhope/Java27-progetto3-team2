package com.develhope.Java27_progetto3_team2.cart.service;

import com.develhope.Java27_progetto3_team2.cart.cart.model.Cart;
import com.develhope.Java27_progetto3_team2.cart.cart.model.CartDTO;
import com.develhope.Java27_progetto3_team2.cart.cart.model.Status;
import com.develhope.Java27_progetto3_team2.cart.cart.repository.CartRepository;
import com.develhope.Java27_progetto3_team2.cart.cartItem.model.CartItem;
import com.develhope.Java27_progetto3_team2.cart.cartItem.repository.CartItemRepository;
import com.develhope.Java27_progetto3_team2.cart.mapper.CartItemMapper;
import com.develhope.Java27_progetto3_team2.cart.mapper.CartMapper;
import com.develhope.Java27_progetto3_team2.exception.InvalidRequestException;
import com.develhope.Java27_progetto3_team2.exception.NotFoundException;
import com.develhope.Java27_progetto3_team2.menu.service.MenuItemService;
import com.develhope.Java27_progetto3_team2.order.OrderService;
import com.develhope.Java27_progetto3_team2.restaurant.service.RestaurantService;
import com.develhope.Java27_progetto3_team2.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final OrderService orderService;

    public CartDTO newCartForUser(UserDetails userDetails, Long idRestaurant) {
        Cart newCart = new Cart();
        newCart.setCartItemList(new ArrayList<CartItem>());
        newCart.setUser(userService.getUserByEmail(userDetails.getUsername()));
        newCart.setStatus(Status.OPEN);
        newCart.setRestaurant(restaurantService.getRestaurantById(idRestaurant));
        return cartMapper.cartToCartDTO(cartRepository.save(newCart));
    }

    public CartDTO newCartItemOnCart(Long idCart, Long idMenuItem) {
        Cart cart = cartRepository.getReferenceById(idCart);
        CartItem cartItem = cartItemRepository.findByMenuItemAndCart(menuItemService.getMenuItemById(idMenuItem),cartRepository.getReferenceById(idCart));
        if (cartItem == null){
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setMenuItem(menuItemService.getMenuItemById(idMenuItem));
            cartItem.setQuantity(1);
        }else {
            int quantity = cartItem.getQuantity();
            cartItem.setQuantity(++quantity);
        }
        cartItemRepository.save(cartItem);
        return cartMapper.cartToCartDTO(cart);
    }

    public void deleteCart(Long idCart) {
        cartRepository.deleteById(idCart);
    }

    public CartDTO deleteItemOnCart(Long idCart, Long idMenuItem) throws Exception {
        Cart cart = cartRepository.getReferenceById(idCart);
        CartItem cartItem = cartItemRepository.findByMenuItemAndCart(menuItemService.getMenuItemById(idMenuItem),cartRepository.getReferenceById(idCart));
        if (cartItem == null){
            throw new NotFoundException("This item doesn't exists!");
        }else {
            int quantity = cartItem.getQuantity();
            cartItem.setQuantity(++quantity);
        }
        cartItemRepository.save(cartItem);
        return cartMapper.cartToCartDTO(cart);
    }

    public CartDTO closedCart(Long idCart,String address, String pay) {
        Cart cart = cartRepository.getReferenceById(idCart);
        if (cart.getCartItemList().isEmpty()) { // Controllo che l'ordine abbia almeno un item
            throw new InvalidRequestException("Order must contain at least one menu item.");
        }
        cart.setStatus(Status.CLOSED);
        orderService.addNewOrdeFromCart(cartRepository.save(cart),address,pay);
        return cartMapper.cartToCartDTO(cart);
    }
}
