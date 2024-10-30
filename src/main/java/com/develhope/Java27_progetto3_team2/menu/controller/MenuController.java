package com.develhope.Java27_progetto3_team2.menu.controller;

import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurantMenu/")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/menu/{restaurantId}")
    public ResponseEntity<RestaurantMenuDTO> createMenuForRestaurant(@PathVariable("restaurantId") Long restaurantId){
        RestaurantMenuDTO restaurantMenuDTO = menuService.addMenuToRestaurant(restaurantId);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantMenuDTO);
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<List<MenuItemDTO>> addItemToMenu(@PathVariable("menuId") Long menuId, @RequestBody MenuItemDTO menuItemDTO) {
        List<MenuItemDTO> menuItemDTOList = menuService.addItemToMenu(menuId, menuItemDTO);
        return ResponseEntity.status(HttpStatus.OK).body(menuItemDTOList);
    }
}
