package com.develhope.Java27_progetto3_team2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class RestaurantMenu {
   @Id
   private int id;
   @Setter
   @Column(name = "menu")
   private String menu;
   @OneToOne
   @Column(name = "restaurant_id")
   private int restaurantId;
}
