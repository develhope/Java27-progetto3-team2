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

    @PostMapping("/user/add")
    public ResponseEntity<Review> addReview(@RequestBody CreateReviewDTO createReviewDTO, @AuthenticationPrincipal UserDetails userDetails) {
        Review review = reviewService.addReview(createReviewDTO, userDetails);
        log.debug("Review added to the database: {}", review);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

}