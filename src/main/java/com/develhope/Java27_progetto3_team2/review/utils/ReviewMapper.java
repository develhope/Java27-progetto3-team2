package com.develhope.Java27_progetto3_team2.review.utils;

import com.develhope.Java27_progetto3_team2.review.model.dto.CreateReviewDTO;
import com.develhope.Java27_progetto3_team2.review.model.Review;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    public Review fromDTO(CreateReviewDTO dto, Restaurant restaurant, User user) {
        return Review.builder()
                .restaurant(restaurant)
                .user(user)
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();
    }
}
