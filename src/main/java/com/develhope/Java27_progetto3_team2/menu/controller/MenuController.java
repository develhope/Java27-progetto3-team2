package com.develhope.Java27_progetto3_team2.menu.controller;

import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.menu.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantMenu/")
public class MenuController {
    private MenuService menuService;


    @PostMapping("/menu/{restaurantId}")
    public ResponseEntity<?> createMenuForRestaurant(@PathVariable("restaurantId") Long restaurantId){
        try{
            RestaurantMenuDTO restaurantMenuDTO = menuService.addMenuToRestaurant(restaurantId);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurantMenuDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{menuId}/")
    public ResponseEntity<?> addItemToMenu(@PathVariable("menuId") Long menuId, @RequestBody MenuItemDTO menuItemDTO) {
        try {
            List<MenuItemDTO> restaurantMenuDTO = menuService.addItemToMenu(menuId, menuItemDTO);
            return ResponseEntity.status(HttpStatus.OK).body(restaurantMenuDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
