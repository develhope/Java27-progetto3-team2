package com.develhope.Java27_progetto3_team2.entity;

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
    @Column(name = "name_restaurant")
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
    @Column(name = "menu_restaurant_id")
    private long menuRestaurantID;
    @Setter
    @Column(name = "review")
    private double review;
}
