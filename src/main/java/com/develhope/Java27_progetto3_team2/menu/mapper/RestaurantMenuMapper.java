package com.develhope.Java27_progetto3_team2.menu.mapper;

import com.develhope.Java27_progetto3_team2.menu.model.RestaurantMenu;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.restaurant.utils.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantMenuMapper {

    private final RestaurantMapper restaurantMapper;

    public RestaurantMenuDTO toDTO (RestaurantMenu restaurantMenu){
        RestaurantMenuDTO restaurantMenuDTO = new RestaurantMenuDTO();
        restaurantMenuDTO.setMenuItemsList(restaurantMenu.getMenuItemsList());
        restaurantMenuDTO.setRestaurantId(restaurantMenu.getRestaurantId());
        return restaurantMenuDTO;
    }

    public RestaurantMenu toRestaurantMenu (RestaurantMenuDTO restaurantMenuDTO){
        RestaurantMenu restaurantMenu = new RestaurantMenu();
        restaurantMenu.setMenuItemsList(restaurantMenuDTO.getMenuItemsList());
        return restaurantMenu;
    }
}
