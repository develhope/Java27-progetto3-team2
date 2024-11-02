package com.develhope.Java27_progetto3_team2.cart.controller;

import com.develhope.Java27_progetto3_team2.cart.cart.model.CartDTO;
import com.develhope.Java27_progetto3_team2.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping()
    public ResponseEntity<?> newCartForUser(@RequestParam Long idUser, @RequestParam Long idRestaurant){
        try{
            CartDTO newCartDTO = cartService.newCartForUser(idUser,idRestaurant);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCartDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{idCart}/{idMenuItem}")
    public ResponseEntity<?> newCartItem(@PathVariable("idCart") Long idCart, @PathVariable("idMenuItem") Long idMenuItem){
        try{
            CartDTO insertedCartItem = cartService.newCartItemOnCart(idCart, idMenuItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(insertedCartItem);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{idCart}")
    public ResponseEntity<?> deleteCart(@PathVariable("idCart") Long idCart){
        cartService.deleteCart(idCart);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted!");
    }

    @DeleteMapping("/{idCart}/{idMenuItem}")
    public ResponseEntity<?> deleteItemOnCart(@PathVariable("idCart") Long idCart,@PathVariable("idMenuItem")  Long idMenuItem){
        try{
        cartService.deleteItemOnCart(idCart, idMenuItem);
        return ResponseEntity.status(HttpStatus.OK).body("Item deleted!");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{idCart}")
    public ResponseEntity<?> closeCart(@PathVariable("idCart") Long idCart, @RequestParam String address, @RequestParam String pay){
        try{
            CartDTO closedCartDTO = cartService.closedCart(idCart, address, pay);
            return ResponseEntity.status(HttpStatus.OK).body(closedCartDTO);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
