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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewMapper reviewMapper;

    public Review addReview(CreateReviewDTO createReviewDTO, UserDetails userDetails) {
        // Ricavare l'utente autenticato usando il repository utenti
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        // Verifica l'esistenza del ristorante
        Restaurant restaurant = restaurantRepository.findById(createReviewDTO.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Ristorante non trovato"));

        // Verifica se l'utente ha ordinato da questo ristorante
        List<Order> orders = orderRepository.findByUserIdAndRestaurantId(user.getId(), createReviewDTO.getRestaurantId());
        if (orders.isEmpty()) {
            throw new InvalidRequestException("L'utente non ha effettuato ordini presso questo ristorante");
        }

        // Utilizzo il ReviewMapper per creare l'entità Review
        Review review = reviewMapper.fromDTO(createReviewDTO, restaurant, user);

        return reviewRepository.save(review);
    }
}