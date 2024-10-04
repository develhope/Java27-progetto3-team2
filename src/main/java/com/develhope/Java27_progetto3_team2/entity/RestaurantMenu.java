package com.develhope.Java27_progetto3_team2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "restaurant_menu")
public class RestaurantMenu {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @OneToOne
   @Column(name = "restaurant_id")
   private Long restaurantId;
   @OneToMany
   @JoinColumn(name = "menu_item_list", referencedColumnName = "menu_item")
   @Setter
   private List<MenuItem> menuItemsList;

}
