package com.develhope.Java27_progetto3_team2.restaurant.model;

import com.develhope.Java27_progetto3_team2.cart.cart.model.Cart;
import com.develhope.Java27_progetto3_team2.restaurant.enumerator.Category;
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

    @Setter
    private Long userId;

    @Setter
    private Long restaurantMenuId;

    @Setter
    private double review;

    @OneToMany(mappedBy = "restaurant")
    private List<Cart> carts;
}