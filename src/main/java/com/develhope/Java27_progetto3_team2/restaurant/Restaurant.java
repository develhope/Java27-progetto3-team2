package com.develhope.Java27_progetto3_team2.restaurant;

import com.develhope.Java27_progetto3_team2.menu.RestaurantMenu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.TimeZone;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity(name = "restaurant")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @JoinColumn(name = "name_restaurant")
    private String nameRestaurant;
    @Setter
    @Column(name = "category")
    private String category;
    @Setter
    @Column(name = "address")
    private String address;
    @Setter
    @Column(name = "opening_hours")
    public TimeZone openingHours;
    @Setter
    @OneToOne
    @JoinColumn(name = "menu_restaurant_id")
    private RestaurantMenu menuRestaurantID;
    @Setter
    @Column(name = "review")
    private double review;
}