package com.develhope.Java27_progetto3_team2.menu.controller;

import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.menu.service.MenuService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurantMenu/")
public class MenuController {
    private MenuService menuService;


    @GetMapping("{idRestaurant}")
    public ResponseEntity<?> getRestaurantMenu(@PathParam("idRestaurant") Long idRestaurant){
       try{
           RestaurantMenuDTO restaurantMenuDTO = menuService.getRestaurantMenu(idRestaurant);
           return ResponseEntity.ok(restaurantMenuDTO);
       }catch (Exception e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
       }
    }

    @GetMapping("{idRestaurant}/items")
    public ResponseEntity<?> getRestaurantMenuItem(@PathParam("idRestaurant") Long idRestaurant){
        try{
            List<MenuItemDTO> restaurantMenuItemDTO = menuService.getRestaurantMenuItem(idRestaurant);
            return ResponseEntity.ok(restaurantMenuItemDTO);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
