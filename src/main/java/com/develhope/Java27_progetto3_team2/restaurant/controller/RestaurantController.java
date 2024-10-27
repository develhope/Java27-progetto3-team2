package com.develhope.Java27_progetto3_team2.restaurant.controller;

import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.model.dto.RestaurantDTO;
import com.develhope.Java27_progetto3_team2.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/public/restaurants")
    public ResponseEntity<Page<RestaurantDTO>> getAllRestaurant(@RequestParam int page, @RequestParam int quantity) {
        Page<RestaurantDTO> restaurantDTOList = restaurantService.getAllRestaurants(page,quantity);
        return ResponseEntity.status(HttpStatus.FOUND).body(restaurantDTOList);
    }

    @GetMapping("/admin/restaurat/{restaurantId}")
    public ResponseEntity<?> getRestaurantById(@PathVariable("restaurantId") Long id){
        try {
            RestaurantDTO restaurantDTO = restaurantService.getRestaurantById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(restaurantDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/public/category")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantByCategory(@RequestParam String category){
        List<RestaurantDTO> restaurantDTOList = restaurantService.getRestaurantByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOList);
    }

    @PostMapping("/manager/add")
    public ResponseEntity<?> addRestaurant(@RequestBody Restaurant restaurant){
        RestaurantDTO restaurantDTO = restaurantService.addRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTO);
    }


    @GetMapping("/public/menu/items/{idRestaurant}")
    public ResponseEntity<?> getRestaurantMenuItem(@PathVariable("idRestaurant") Long idRestaurant){
        try{
            List<MenuItemDTO> restaurantMenuItemDTO = restaurantService.getRestaurantMenuItem(idRestaurant);
            return ResponseEntity.ok(restaurantMenuItemDTO);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @GetMapping("/public/menu/{idRestaurant}")
    public ResponseEntity<?> getRestaurantMenu(@PathVariable("idRestaurant") Long idRestaurant){
        try{
            RestaurantMenuDTO restaurantMenuDTO = restaurantService.getRestaurantMenu(idRestaurant);
            return ResponseEntity.ok(restaurantMenuDTO);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
