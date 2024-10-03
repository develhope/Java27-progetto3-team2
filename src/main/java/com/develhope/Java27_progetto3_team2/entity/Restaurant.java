package com.develhope.Java27_progetto3_team2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.TimeZone;

@Entity
public class Restaurant {
    @Id
    private int id;
    @Column(name = "nameRestaurant")
    private String nameRestaurant;
    @Column(name = "category")
    private String category;
    @Column(name = "address")
    private String address;
    @Column(name = "openingHours")
    public TimeZone openingHours;
    @Column(name = "menu")
    private MenuRestaurant menuRestaurant;
    @Column(name = "review")
    private double review;
}
