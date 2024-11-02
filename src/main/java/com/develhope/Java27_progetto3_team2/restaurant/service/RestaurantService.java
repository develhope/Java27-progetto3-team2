package com.develhope.Java27_progetto3_team2.restaurant.service;

import com.develhope.Java27_progetto3_team2.exception.NotFoundException;
import com.develhope.Java27_progetto3_team2.menu.mapper.MenuItemMapper;
import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.menu.service.MenuService;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.model.dto.RestaurantDTO;
import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import com.develhope.Java27_progetto3_team2.restaurant.utils.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final MenuService menuService;
    private final MenuItemMapper menuItemMapper;

    public Page<RestaurantDTO> getAllRestaurants(int page, int quantity){
        Pageable pageable = PageRequest.of(page,quantity);
        Page<Restaurant> restaurantsList = restaurantRepository.findAll(pageable);
        return restaurantsList.map(restaurantMapper::toDTO);
    }

    public RestaurantDTO getRestaurantDTOById(Long id) {
        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant with id: " + id + " not found!"));
        return restaurantMapper.toDTO(restaurant);
    }

    public Restaurant getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Restaurant with id: " + id + " not found!"));
        return restaurant;
    }

    public List<RestaurantDTO> getRestaurantByCategory(String category){
        List<Restaurant> restaurants = restaurantRepository.findByCategory(category);
        return restaurants
                .stream()
                .map(restaurantMapper::toDTO).toList();
    }

    public RestaurantDTO addRestaurant(Restaurant restaurant){
        RestaurantDTO restaurantDTO = restaurantMapper.toDTO(restaurant);
        Restaurant finalRestaurant = restaurantMapper.toRestaurant(restaurantDTO);
        restaurantRepository.save(finalRestaurant);
        menuService.saveMenuToRestaurant(finalRestaurant);
        return restaurantMapper.toDTO(finalRestaurant);
    }

    public RestaurantMenuDTO getRestaurantMenu(Long restaurantId) {
        return getRestaurantDTOById(restaurantId).getMenuRestaurant();
    }

    public List<MenuItemDTO> getRestaurantMenuItem(Long restaurantId) {
        List<MenuItem> menuItemList = getRestaurantDTOById(restaurantId).getMenuRestaurant().getMenuItemsList();
        List<MenuItemDTO> menuItemDTOList = new ArrayList<>();
        menuItemList.forEach(a -> menuItemDTOList.add(menuItemMapper.menuItemToMenuItemDTO(a)));
        return menuItemDTOList;
    }
}
