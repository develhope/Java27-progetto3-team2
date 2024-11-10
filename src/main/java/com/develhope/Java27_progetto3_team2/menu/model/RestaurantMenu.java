package com.develhope.Java27_progetto3_team2.menu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "restaurant_menu")
public class RestaurantMenu {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Setter
   private Long restaurantId;

   @OneToMany(mappedBy = "restaurantMenu")
   @Setter
   private List<MenuItem> menuItemsList = new ArrayList<>();

}
