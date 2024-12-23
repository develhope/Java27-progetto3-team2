package com.develhope.Java27_progetto3_team2.restaurant.service;

import com.develhope.Java27_progetto3_team2.exception.exceptions.EntityNotFoundException;
import com.develhope.Java27_progetto3_team2.menu.mapper.MenuItemMapper;
import com.develhope.Java27_progetto3_team2.menu.mapper.RestaurantMenuMapper;
import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.model.RestaurantMenu;
import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.menu.repository.RestaurantMenuRepository;
import com.develhope.Java27_progetto3_team2.menu.service.MenuService;
import com.develhope.Java27_progetto3_team2.restaurant.enumerator.Category;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.model.dto.RestaurantDTO;
import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import com.develhope.Java27_progetto3_team2.restaurant.utils.RestaurantMapper;
import com.develhope.Java27_progetto3_team2.user.User;
import com.develhope.Java27_progetto3_team2.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserService userService;
    private final RestaurantMenuRepository menuRepository;
    private final RestaurantMenuMapper restaurantMenuMapper;

    public Page<RestaurantDTO> getAllRestaurants(int page, int quantity){
        Pageable pageable = PageRequest.of(page,quantity);
        Page<Restaurant> restaurantsList = restaurantRepository.findAll(pageable);
        return restaurantsList.map(restaurantMapper::toDTO);
    }

    public RestaurantDTO getRestaurantDTOById(Long id) {
        Restaurant restaurant = getRestaurantById(id);
        return restaurantMapper.toDTO(restaurant);
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id: " + id + " not found!"));
    }

    public List<RestaurantDTO> getRestaurantByCategory(String category){
        List<Restaurant> restaurants = restaurantRepository.findByCategory(category);
        return restaurants
                .stream()
                .map(restaurantMapper::toDTO).toList();
    }

    public RestaurantDTO addRestaurant(Restaurant restaurant, UserDetails userDetails){
        User user = (User) userDetails;
        restaurant.setUserId(user.getId());
        restaurantRepository.save(restaurant);

        RestaurantMenu restaurantMenu = menuService.saveMenuToRestaurant(restaurant);
        restaurant.setRestaurantMenuId(restaurantMenu.getId());

        restaurantRepository.save(restaurant);
        userService.addRestaurantToUser(userDetails, restaurant);

        return restaurantMapper.toDTO(restaurant);
    }

    public RestaurantMenuDTO getRestaurantMenu(Long restaurantId) {
        RestaurantMenu restaurantMenu = menuRepository.findRestaurantMenuById(restaurantId).orElseThrow(() -> new EntityNotFoundException("Restaurant menu with id: " + restaurantId + " not found"));
        return restaurantMenuMapper.toDTO(restaurantMenu);
    }

    public List<MenuItemDTO> getRestaurantMenuItem(Long restaurantId) {
        RestaurantMenu restaurantMenu = menuRepository.findRestaurantMenuById(restaurantId).orElseThrow(() -> new EntityNotFoundException("Restaurant menu with id: " + restaurantId + " not found"));

        List<MenuItem> menuItemList = restaurantMenu.getMenuItemsList();
        List<MenuItemDTO> menuItemDTOList = new ArrayList<>();
        menuItemList.forEach(a -> menuItemDTOList.add(menuItemMapper.menuItemToMenuItemDTO(a)));
        return menuItemDTOList;
    }


    /***
     *Change restaurant details by inserting the restaurant id,
     * the field you want to change,
     * the value you want to apply to that field
     * @param restaurantId id of the restaurant you want to modify
     * @param field fields: (nameRestaurant, category, address )
     * @param value category: ( PIZZERIA,JAPANESE,INDIAN,CHINESE,FAST_FOOD,BUFFET)
     * @return returns the RestaurantDTO
     */
    public RestaurantDTO changeRestaurantDetails(Long restaurantId, String field, String value){
        Restaurant restaurant = getRestaurantById(restaurantId);
        switch (field){
            case "nameRestaurant" -> restaurant.setNameRestaurant(value);
            case "category" -> restaurant.setCategory(Category.valueOf(value));
            case "address" -> restaurant.setAddress(value);
        }
        restaurantRepository.save(restaurant);
        return restaurantMapper.toDTO(restaurant);
    }
}
