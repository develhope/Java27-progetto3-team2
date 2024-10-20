package com.develhope.Java27_progetto3_team2.restaurant.model.dto;

import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.restaurant.enumerator.Category;
import lombok.*;

import java.util.TimeZone;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDTO {
    private Long id;
    @Setter
    private String nameRestaurant;
    @Setter
    private Category category;
    @Setter
    private String address;
    @Setter
    public TimeZone openingHours;
    @Setter
    private RestaurantMenuDTO menuRestaurant;
    @Setter
    private double review;
}
