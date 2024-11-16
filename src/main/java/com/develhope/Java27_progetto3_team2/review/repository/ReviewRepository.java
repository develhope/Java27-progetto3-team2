package com.develhope.Java27_progetto3_team2.review.repository;

import com.develhope.Java27_progetto3_team2.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRestaurantId(Long restaurantId);
    List<Review> findByUserId(Long userId);
}
