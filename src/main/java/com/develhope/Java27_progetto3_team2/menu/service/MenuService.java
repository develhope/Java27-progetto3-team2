package com.develhope.Java27_progetto3_team2.menu.service;


import com.develhope.Java27_progetto3_team2.exception.exceptions.InvalidRequestException;
import com.develhope.Java27_progetto3_team2.exception.exceptions.EntityNotFoundException;
import com.develhope.Java27_progetto3_team2.menu.mapper.MenuItemMapper;
import com.develhope.Java27_progetto3_team2.menu.mapper.RestaurantMenuMapper;
import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.model.RestaurantMenu;
import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.menu.repository.MenuItemRepository;
import com.develhope.Java27_progetto3_team2.menu.repository.RestaurantMenuRepository;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMenuRepository restaurantMenuRepository;
    private final MenuItemMapper menuItemMapper;
    private final RestaurantMenuMapper restaurantMenuMapper;
    private final MenuItemRepository menuItemRepository;


    public RestaurantMenuDTO addMenuToRestaurant(UserDetails userDetails) {
        Restaurant restaurant = restaurantRepository.findByUser_Email(userDetails.getUsername());
        RestaurantMenu restaurantMenu = new RestaurantMenu(restaurant);
        restaurantMenuRepository.save(restaurantMenu);
        return restaurantMenuMapper.toDTO(restaurantMenu);
    }


    @Transactional
    public List<MenuItemDTO> addItemToMenu(UserDetails userDetails, MenuItem menuItem) {
        Restaurant restaurant = restaurantRepository.findByUser_Email(userDetails.getUsername());
        RestaurantMenu restaurantMenu = restaurantMenuRepository.findRestaurantMenuById(restaurant.getId()).orElseThrow(() -> new NotFoundException("No menu found"));
        menuItem.setRestaurantMenu(restaurantMenu);
        menuItemRepository.save(menuItem);
        List<MenuItem> menuItemList = restaurantMenu.getMenuItemsList();
        menuItemList.add(menuItem);
        restaurantMenu.setMenuItemsList(menuItemList);
        restaurantMenuRepository.save(restaurantMenu);
        return menuItemList.stream().map(menuItemMapper::menuItemToMenuItemDTO).toList();
    }

    //This method is mainly used inside RestaurantService to avoid injecting RestaurantMenu repository
    // inside RestaurantService.
    public RestaurantMenu saveMenuToRestaurant(Restaurant restaurant) {
        RestaurantMenu restaurantMenu = new RestaurantMenu(restaurant);
        return restaurantMenuRepository.save(restaurantMenu);
    }



}

