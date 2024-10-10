package com.develhope.Java27_progetto3_team2.restaurant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.TimeZone;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private Long id;
    @Setter
    private String nameRestaurant;
    @Setter
    private String category;
    @Setter
    private String address;
    @Setter
    public TimeZone openingHours;
    @Setter
    private long menuRestaurant;
    @Setter
    private double review;
}
