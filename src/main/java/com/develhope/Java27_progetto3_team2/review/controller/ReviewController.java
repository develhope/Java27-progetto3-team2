package com.develhope.Java27_progetto3_team2.review.controller;

import com.develhope.Java27_progetto3_team2.review.model.Review;
import com.develhope.Java27_progetto3_team2.review.model.dto.CreateReviewDTO;
import com.develhope.Java27_progetto3_team2.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reviews")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/restaurant/{restaurantId}") //Tutte le recensioni di un ristorante
    public ResponseEntity<Page<Review>> getReviewsByRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET /reviews/restaurant/{} - Fetching paginated reviews (page={}, size={})", restaurantId, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewService.getReviewsByRestaurant(restaurantId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/user/{userId}") //Tutte le recensioni di un utente
    public ResponseEntity<Page<Review>> getReviewsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET /reviews/user/{} - Fetching paginated reviews (page={}, size={})", userId, page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviews = reviewService.getReviewsByUser(userId, pageable);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{id}") //Singola recensione
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        Review review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/add") //Nuova recensione
    public ResponseEntity<Review> addReview(@Valid @RequestBody CreateReviewDTO createReviewDTO, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("POST /reviews - Request to add review for restaurant ID: {}", createReviewDTO.getRestaurantId());
        Review review = reviewService.addReview(createReviewDTO, userDetails);
        log.info("POST /reviews - Review added with ID: {}", review.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @PutMapping("/{id}") //Aggiorna una recensione esistente
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @Valid @RequestBody CreateReviewDTO createReviewDTO, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("PUT /reviews/{} - Request to update review", id);
        Review updatedReview = reviewService.updateReview(id, createReviewDTO, userDetails);
        log.info("PUT /reviews/{} - Review updated successfully", id);
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{id}") //Cancella una recensione
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        log.info("DELETE /reviews/{} - Request to delete review", id);
        reviewService.deleteReview(id);
        log.info("DELETE /reviews/{} - Review deleted successfully", id);
        return ResponseEntity.noContent().build();
    }

}