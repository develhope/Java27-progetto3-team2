package com.develhope.Java27_progetto3_team2.model;

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
    private Long restaurantId;
}
