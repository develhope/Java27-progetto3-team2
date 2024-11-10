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
        restaurantDTO.setRestaurantMenuId(restaurant.getId());
        restaurantDTO.setReview(restaurant.getReview());
        return restaurantDTO;

    }

}
