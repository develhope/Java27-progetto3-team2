package com.develhope.Java27_progetto3_team2.menu.service;

import com.develhope.Java27_progetto3_team2.menu.mapper.MenuItemMapper;
import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.menu.repository.RestaurantMenuRepository;
import com.develhope.Java27_progetto3_team2.restaurant.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

public class MenuService {
    private RestaurantService restaurantService;
    private RestaurantMenuRepository restaurantMenuRepository;
    private MenuItemMapper menuItemMapper;

    public RestaurantMenuDTO getRestaurantMenu(Long idRestaurant) throws Exception {
        return restaurantService.getRestaurantById(idRestaurant).getMenuRestaurant();
    }

    public List<MenuItemDTO> getRestaurantMenuItem(Long idRestaurant) throws Exception {
         List<MenuItem> menuItemList = restaurantService.getRestaurantById(idRestaurant).getMenuRestaurant().getMenuItemsList();
         List<MenuItemDTO> menuItemDTOList = new ArrayList<>();
         menuItemList.forEach(a -> menuItemDTOList.add(menuItemMapper.menuItemToMenuItemDTO(a)));
         return menuItemDTOList;
    }
}
