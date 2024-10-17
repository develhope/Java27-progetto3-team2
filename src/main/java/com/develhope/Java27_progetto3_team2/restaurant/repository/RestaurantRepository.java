package com.develhope.Java27_progetto3_team2.restaurant.repository;

import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    List<Restaurant> findByCategory(String category);
    Page<Restaurant> findAllPaginated(Pageable pageable);
}
