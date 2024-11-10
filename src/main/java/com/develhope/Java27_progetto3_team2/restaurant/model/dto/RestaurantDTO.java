package com.develhope.Java27_progetto3_team2.restaurant.model.dto;

import com.develhope.Java27_progetto3_team2.restaurant.enumerator.Category;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDTO {
    private String nameRestaurant;
    private Category category;
    private String address;
    public LocalDateTime openingHours;
    private Long restaurantMenuId;
    private double review;
}
