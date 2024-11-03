package com.develhope.Java27_progetto3_team2.menu.controller;

import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/manager/menu")
    public ResponseEntity<RestaurantMenuDTO> createMenuForRestaurant(@AuthenticationPrincipal UserDetails userDetails){
        RestaurantMenuDTO restaurantMenuDTO = menuService.addMenuToRestaurant(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantMenuDTO);
    }

    @PatchMapping("/manager/menu_item")
    public ResponseEntity<List<MenuItemDTO>> addItemToMenu(@AuthenticationPrincipal UserDetails userDetails, @RequestBody MenuItem menuItem) {
        List<MenuItemDTO> menuItemDTOList = menuService.addItemToMenu(userDetails, menuItem);
        return ResponseEntity.status(HttpStatus.OK).body(menuItemDTOList);
    }
}
