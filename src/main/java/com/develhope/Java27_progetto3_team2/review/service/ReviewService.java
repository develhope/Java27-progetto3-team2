package com.develhope.Java27_progetto3_team2.review.service;

import com.develhope.Java27_progetto3_team2.exception.exceptions.EntityNotFoundException;
import com.develhope.Java27_progetto3_team2.exception.exceptions.InvalidRequestException;
import com.develhope.Java27_progetto3_team2.order.entity.Order;
import com.develhope.Java27_progetto3_team2.review.model.Review;
import com.develhope.Java27_progetto3_team2.review.model.dto.CreateReviewDTO;
import com.develhope.Java27_progetto3_team2.review.repository.ReviewRepository;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import com.develhope.Java27_progetto3_team2.user.User;
import com.develhope.Java27_progetto3_team2.user.UserRepository;
import com.develhope.Java27_progetto3_team2.order.repository.OrderRepository;
import com.develhope.Java27_progetto3_team2.review.utils.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewMapper reviewMapper;

    public Review addReview(CreateReviewDTO createReviewDTO, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> {
                    log.error("User with email {} not found", userDetails.getUsername());
                    return new EntityNotFoundException("Utente non trovato");
                });

        Restaurant restaurant = restaurantRepository.findById(createReviewDTO.getRestaurantId())
                .orElseThrow(() -> {
                    log.error("Restaurant with ID {} not found", createReviewDTO.getRestaurantId());
                    return new EntityNotFoundException("Ristorante non trovato");
                });

        Review review = reviewMapper.fromDTO(createReviewDTO, restaurant, user);
        Review savedReview = reviewRepository.save(review);
        log.info("Review saved with ID: {}", savedReview.getId());
        return savedReview;
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Review with ID {} not found", id);
                    return new EntityNotFoundException("Recensione non trovata");
                });
    }

    public Review updateReview(Long id, CreateReviewDTO createReviewDTO, UserDetails userDetails) {
        Review existingReview = getReviewById(id);
        if (!existingReview.getUser().getEmail().equals(userDetails.getUsername())) {
            log.warn("User {} attempted to update a review not owned by them", userDetails.getUsername());
            throw new InvalidRequestException("Non puoi modificare recensioni di altri utenti");
        }
        existingReview.setRating(createReviewDTO.getRating());
        existingReview.setComment(createReviewDTO.getComment());
        Review updatedReview = reviewRepository.save(existingReview);
        log.info("Review with ID {} updated successfully", id);
        return updatedReview;

    }

    public void deleteReview(Long id) {
        Review review = getReviewById(id);
        reviewRepository.delete(review);
        log.info("Review with ID {} deleted successfully", id);
    }

    public Page<Review> getReviewsByRestaurant(Long restaurantId, Pageable pageable) {
        log.info("Fetching paginated reviews for restaurant ID: {}", restaurantId);
        return reviewRepository.findByRestaurantId(restaurantId, pageable);
    }

    public Page<Review> getReviewsByUser(Long userId, Pageable pageable) {
        log.info("Fetching paginated reviews for user ID: {}", userId);
        return reviewRepository.findByUserId(userId, pageable);
    }
}