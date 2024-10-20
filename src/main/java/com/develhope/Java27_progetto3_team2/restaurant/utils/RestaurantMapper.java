package com.develhope.Java27_progetto3_team2.restaurant.utils;

import com.develhope.Java27_progetto3_team2.restaurant.model.dto.RestaurantDTO;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantMapper {

    public RestaurantDTO toDTO (Restaurant restaurant){
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        restaurantDTO.setNameRestaurant(restaurant.getNameRestaurant());
        restaurantDTO.setCategory(restaurant.getCategory());
        restaurantDTO.setAddress(restaurant.getAddress());
        restaurantDTO.setOpeningHours(restaurant.getOpeningHours());
        restaurantDTO.setReview(restaurant.getReview());
        return restaurantDTO;

    }

    public Restaurant toRestaurant (RestaurantDTO restaurantDTO){
        Restaurant restaurant = new Restaurant();
        restaurant.setNameRestaurant(restaurantDTO.getNameRestaurant());
        restaurant.setCategory(restaurantDTO.getCategory());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setOpeningHours(restaurantDTO.getOpeningHours());
        restaurant.setReview(restaurantDTO.getReview());
        return restaurant;

    }
}
