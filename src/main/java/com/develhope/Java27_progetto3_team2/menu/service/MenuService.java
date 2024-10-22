package com.develhope.Java27_progetto3_team2.menu.service;

import com.develhope.Java27_progetto3_team2.menu.mapper.MenuItemMapper;
import com.develhope.Java27_progetto3_team2.menu.mapper.RestaurantMenuMapper;
import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.model.RestaurantMenu;
import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.menu.repository.RestaurantMenuRepository;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import com.develhope.Java27_progetto3_team2.restaurant.service.RestaurantService;
import com.develhope.Java27_progetto3_team2.restaurant.utils.RestaurantMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    private RestaurantRepository restaurantRepository;
    private RestaurantMapper restaurantMapper;
    private RestaurantService restaurantService;
    private RestaurantMenuRepository restaurantMenuRepository;
    private MenuItemMapper menuItemMapper;
    private RestaurantMenuMapper restaurantMenuMapper;

    public RestaurantMenuDTO getRestaurantMenu(Long idRestaurant) throws Exception {

        return restaurantService.getRestaurantById(idRestaurant).getMenuRestaurant();
    }

    public List<MenuItemDTO> getRestaurantMenuItem(Long idRestaurant) throws Exception {
         List<MenuItem> menuItemList = restaurantService.getRestaurantById(idRestaurant).getMenuRestaurant().getMenuItemsList();
         List<MenuItemDTO> menuItemDTOList = new ArrayList<>();
         menuItemList.forEach(a -> menuItemDTOList.add(menuItemMapper.menuItemToMenuItemDTO(a)));
         return menuItemDTOList;
    }

    public RestaurantMenuDTO addMenuToRestaurant(Long idRestaurant)throws Exception {
        Restaurant restaurant = restaurantRepository.findById(idRestaurant).orElseThrow(() -> new Exception("No restaurant found with id: " + idRestaurant));
        RestaurantMenu restaurantMenu = new RestaurantMenu(restaurant);
        restaurantMenuRepository.save(restaurantMenu);
        return restaurantMenuMapper.toDTO(restaurantMenu);
    }

    public List<MenuItemDTO> addItemToMenu(Long menuId, MenuItemDTO menuItemDTO) throws Exception{
        RestaurantMenu restaurantMenu = restaurantMenuRepository.findRestaurantMenuById(menuId).orElseThrow(() -> new Exception("No menu found"));

        menuItemDTO.setMenuId(menuId);

        List<MenuItem> menuItemList = restaurantMenu.getMenuItemsList();
        menuItemList.add(menuItemMapper.menuItemDTOToMenuItem(menuItemDTO));

        restaurantMenu.setMenuItemsList(menuItemList);
        restaurantMenuRepository.save(restaurantMenu);

        return menuItemList.stream().map( menuItem -> menuItemMapper.menuItemToMenuItemDTO(menuItem)).toList();

    }
}
