package com.develhope.Java27_progetto3_team2.restaurant.model;

import com.develhope.Java27_progetto3_team2.cart.cart.model.Cart;
import com.develhope.Java27_progetto3_team2.menu.model.RestaurantMenu;
import com.develhope.Java27_progetto3_team2.restaurant.enumerator.Category;
import com.develhope.Java27_progetto3_team2.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime openingHours;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    @OneToOne
    @JoinColumn(name = "menu_restaurant_id")
    private RestaurantMenu menuRestaurantID;

    @Setter
    private double review;

    @OneToMany(mappedBy = "restaurant")
    private List<Cart> carts;
}