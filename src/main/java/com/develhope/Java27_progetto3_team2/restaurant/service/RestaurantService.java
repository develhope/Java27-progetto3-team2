package com.develhope.Java27_progetto3_team2.restaurant.service;

import com.develhope.Java27_progetto3_team2.menu.model.RestaurantMenu;
import com.develhope.Java27_progetto3_team2.menu.repository.RestaurantMenuRepository;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.model.dto.RestaurantDTO;
import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import com.develhope.Java27_progetto3_team2.restaurant.utils.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final RestaurantMenuRepository restaurantMenuRepository;

    public Page<RestaurantDTO> getAllRestaurants(int page, int quantity){
        Pageable pageable = PageRequest.of(page,quantity);
        Page<Restaurant> restaurantsList = restaurantRepository.findAll(pageable);
        return restaurantsList.map(restaurantMapper::toDTO);
    }

    public RestaurantDTO getRestaurantById(Long id) throws Exception {
        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() -> new Exception("Restaurant with id: " + id + " not found!"));
        return restaurantMapper.toDTO(restaurant);
    }

    public List<RestaurantDTO> getRestaurantByCategory(String category){
        List<Restaurant> restaurants = restaurantRepository.findByCategory(category);
        return restaurants
                .stream()
                .map(restaurantMapper::toDTO).toList();
    }

    public RestaurantDTO addRestaurant(Restaurant restaurant){
        RestaurantDTO restaurantDTO = restaurantMapper.toDTO(restaurant);

        restaurantDTO.setOpeningHours(LocalDateTime.now());
        Restaurant finalRestaurant = restaurantMapper.toRestaurant(restaurantDTO);
        RestaurantMenu restaurantMenu = new RestaurantMenu(finalRestaurant);
        restaurantRepository.save(finalRestaurant);
        finalRestaurant.setMenuRestaurantID(restaurantMenu);
        restaurantMenuRepository.save(restaurantMenu);

        return restaurantDTO;
    }
}
