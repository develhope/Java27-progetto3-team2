package com.develhope.Java27_progetto3_team2.menu;

import com.develhope.Java27_progetto3_team2.restaurant.utils.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RestaurantMenuMapper {

    private final RestaurantMapper restaurantMapper;

    public RestaurantMenuDTO restaurantMenuDTO (RestaurantMenu restaurantMenu){
        RestaurantMenuDTO restaurantMenuDTO = new RestaurantMenuDTO();
        restaurantMenuDTO.setMenuItemsList(restaurantMenu.getMenuItemsList());
        restaurantMenuDTO.setRestaurantId(restaurantMapper.toDTO(restaurantMenu.getRestaurantId()));
        return restaurantMenuDTO;
    }

    public RestaurantMenu restaurantMenu (RestaurantMenuDTO restaurantMenuDTO){
        RestaurantMenu restaurantMenu = new RestaurantMenu();
        restaurantMenu.setMenuItemsList(restaurantMenuDTO.getMenuItemsList());
        return restaurantMenu;
    }
}
