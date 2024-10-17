package com.develhope.Java27_progetto3_team2.menu;

import com.develhope.Java27_progetto3_team2.restaurant.dto.RestaurantDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RestaurantMenuDTO {
    private Long id;
    @Setter
    private List<MenuItem> menuItemsList;
    @Setter
    private RestaurantDTO restaurantId;
}
