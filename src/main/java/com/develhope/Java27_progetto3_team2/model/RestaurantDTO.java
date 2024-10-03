package com.develhope.Java27_progetto3_team2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.util.TimeZone;

public class RestaurantDTO {
    private int id;
    private String nameRestaurant;
    private String category;
    private String address;
    public TimeZone openingHours;
    private MenuRestaurant menuRestaurant;
    private double review;
}
