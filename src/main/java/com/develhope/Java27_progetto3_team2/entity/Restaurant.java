package com.develhope.Java27_progetto3_team2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.TimeZone;

@Getter
@Entity
public class Restaurant {
    @Id
    private int id;
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
    @OneToOne
    @Column(name = "menu_restaurant_id")
    private int menuRestaurantID;
    @Column(name = "review")
    private double review;
}