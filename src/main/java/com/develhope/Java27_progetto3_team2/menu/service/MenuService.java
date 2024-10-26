package com.develhope.Java27_progetto3_team2.menu.service;

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
    public RestaurantMenuDTO addMenuToRestaurant(Long idRestaurant) throws Exception {
        Restaurant restaurant = restaurantRepository.findById(idRestaurant).orElseThrow(() -> new Exception("No restaurant found with id: " + idRestaurant));
        RestaurantMenu restaurantMenu = new RestaurantMenu(restaurant);
        restaurantMenuRepository.save(restaurantMenu);
        return restaurantMenuMapper.toDTO(restaurantMenu);
    }
    @Transactional
    public List<MenuItemDTO> addItemToMenu(Long menuId, MenuItemDTO menuItemDTO) throws Exception {
        menuItemRepository.save(menuItemMapper.menuItemDTOToMenuItem(menuItemDTO));
        RestaurantMenu restaurantMenu = restaurantMenuRepository.findRestaurantMenuById(menuId).orElseThrow(() -> new Exception("No menu found"));

        menuItemDTO.setMenuId(menuId);

        List<MenuItem> menuItemList = restaurantMenu.getMenuItemsList();
        menuItemList.add(menuItemMapper.menuItemDTOToMenuItem(menuItemDTO));

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
