package com.develhope.Java27_progetto3_team2.restaurant.repository;

import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    List<Restaurant> findByCategory(String category);
}
