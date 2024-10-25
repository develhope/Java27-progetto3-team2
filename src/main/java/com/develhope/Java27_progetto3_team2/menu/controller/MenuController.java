package com.develhope.Java27_progetto3_team2.menu.controller;

import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurantMenu/")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/menu/{restaurantId}")
    public ResponseEntity<?> createMenuForRestaurant(@PathVariable("restaurantId") Long restaurantId){
        try{
            RestaurantMenuDTO restaurantMenuDTO = menuService.addMenuToRestaurant(restaurantId);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurantMenuDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<?> addItemToMenu(@PathVariable("menuId") Long menuId, @RequestBody MenuItemDTO menuItemDTO) {
        try {
            menuService.addItemToMenu(menuId, menuItemDTO);
            return ResponseEntity.status(HttpStatus.OK).body(menuItemDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
