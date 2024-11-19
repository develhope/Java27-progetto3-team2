package com.develhope.Java27_progetto3_team2.review.controller;

import com.develhope.Java27_progetto3_team2.review.model.Review;
import com.develhope.Java27_progetto3_team2.review.model.dto.CreateReviewDTO;
import com.develhope.Java27_progetto3_team2.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews/restaurant/{restaurantId}") //Tutte le recensioni di un ristorante
    public ResponseEntity<List<Review>> getReviewsByRestaurant(@PathVariable Long restaurantId) {
        List<Review> reviews = reviewService.getReviewsByRestaurant(restaurantId);;
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/reviews/user/{userId}") //Tutte le recensioni di un utente
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        List<Review> reviews = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/reviews/{id}") //Singola recensione
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/reviews/add") //Nuova recensione
    public ResponseEntity<Review> addReview(@RequestBody CreateReviewDTO createReviewDTO, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("POST /reviews - Request to add review for restaurant ID: {}", createReviewDTO.getRestaurantId());
        Review review = reviewService.addReview(createReviewDTO, userDetails);
        log.info("POST /reviews - Review added with ID: {}", review.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @PutMapping("/reviews/{id}") //Aggiorna una recensione esistente
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody CreateReviewDTO createReviewDTO, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("PUT /reviews/{} - Request to update review", id);
        Review updatedReview = reviewService.updateReview(id, createReviewDTO, userDetails);
        log.info("PUT /reviews/{} - Review updated successfully", id);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/reviews/{id}") //Cancella una recensione
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        log.info("DELETE /reviews/{} - Request to delete review", id);
        reviewService.deleteReview(id);
        log.info("DELETE /reviews/{} - Review deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

}