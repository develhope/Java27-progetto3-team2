package com.develhope.Java27_progetto3_team2.cart.controller;

import com.develhope.Java27_progetto3_team2.cart.cart.model.CartDTO;
import com.develhope.Java27_progetto3_team2.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class CartController {
    private final CartService cartService;

    @PostMapping("/user/cart")
    public ResponseEntity<?> newCartForUser(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long idRestaurant) {
        CartDTO newCartDTO = cartService.newCartForUser(userDetails, idRestaurant);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCartDTO);
    }

    @PostMapping("/user/{idCart}/{idMenuItem}")
    public ResponseEntity<?> newCartItem(@PathVariable("idCart") Long idCart, @PathVariable("idMenuItem") Long idMenuItem) {

        CartDTO insertedCartItem = cartService.newCartItemOnCart(idCart, idMenuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(insertedCartItem);

    }

    @DeleteMapping("/user/{idCart}")
    public ResponseEntity<?> deleteCart(@PathVariable("idCart") Long idCart) {
        cartService.deleteCart(idCart);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted!");
    }

    @DeleteMapping("/user/{idCart}/{idMenuItem}")
    public ResponseEntity<?> deleteItemOnCart(@PathVariable("idCart") Long idCart, @PathVariable("idMenuItem") Long idMenuItem) throws Exception {

        cartService.deleteItemOnCart(idCart, idMenuItem);
        return ResponseEntity.status(HttpStatus.OK).body("Item deleted!");

    }

    @PatchMapping("/user/{idCart}")
    public ResponseEntity<?> closeCart(@PathVariable("idCart") Long idCart, @RequestParam String address, @RequestParam String pay) {

        CartDTO closedCartDTO = cartService.closedCart(idCart, address, pay);
        return ResponseEntity.status(HttpStatus.OK).body(closedCartDTO);


    }

}
