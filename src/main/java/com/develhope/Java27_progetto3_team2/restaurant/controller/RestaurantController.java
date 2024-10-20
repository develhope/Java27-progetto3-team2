package com.develhope.Java27_progetto3_team2.restaurant.controller;

import com.develhope.Java27_progetto3_team2.restaurant.model.dto.RestaurantDTO;
import com.develhope.Java27_progetto3_team2.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping()
    public ResponseEntity<Page<RestaurantDTO>> getAllRestaurant(@RequestParam int page, @RequestParam int quantity) {
        Page<RestaurantDTO> restaurantDTOList = restaurantService.getAllRestaurants(page,quantity);
        return ResponseEntity.status(HttpStatus.FOUND).body(restaurantDTOList);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<?> getRestaurantById(@PathVariable("restaurantId") Long id){
        try {
            RestaurantDTO restaurantDTO = restaurantService.getRestaurantById(id);
            return ResponseEntity.status(HttpStatus.FOUND).body(restaurantDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/category")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantByCategory(@RequestParam String category){
        List<RestaurantDTO> restaurantDTOList = restaurantService.getRestaurantByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantDTOList);
    }
}
