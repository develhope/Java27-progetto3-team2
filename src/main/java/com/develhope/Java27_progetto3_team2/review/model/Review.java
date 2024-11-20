package com.develhope.Java27_progetto3_team2.review.model;


import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.user.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int rating;

    @Column(length = 500)
    private String comment;
}
