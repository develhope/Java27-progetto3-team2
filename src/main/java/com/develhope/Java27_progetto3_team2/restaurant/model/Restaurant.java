package com.develhope.Java27_progetto3_team2.restaurant.model;

import com.develhope.Java27_progetto3_team2.menu.model.RestaurantMenu;
import com.develhope.Java27_progetto3_team2.restaurant.enumerator.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.TimeZone;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Builder
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @JoinColumn(name = "name_restaurant")
    private String nameRestaurant;
    @Setter
    @Enumerated(EnumType.STRING)
    private Category category;
    @Setter
    private String address;
    @Setter
    @Column(name = "opening_hours")
    public TimeZone openingHours;
    @Setter
    @OneToOne
    @JoinColumn(name = "menu_restaurant_id")
    private RestaurantMenu menuRestaurantID;
    @Setter
    private double review;
}