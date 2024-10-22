package com.develhope.Java27_progetto3_team2.menu.mapper;

import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.repository.RestaurantMenuRepository;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {
    RestaurantMenuRepository restaurantMenuRepository;
    public MenuItemDTO menuItemToMenuItemDTO (MenuItem restaurantMenuItem){
        return MenuItemDTO
                .builder()
                .id(restaurantMenuItem.getId())
                .itemName(restaurantMenuItem.getItemName())
                .itemDescription(restaurantMenuItem.getItemDescription())
                .itemPrice(restaurantMenuItem.getItemPrice())
                .categoryFood(restaurantMenuItem.getCategoryFood())
                .availability(restaurantMenuItem.isAvailability())
                .vegetarianItem(restaurantMenuItem.isVegetarianItem())
                .glutenFreeItem(restaurantMenuItem.isGlutenFreeItem())
                .lactoseFreeItem(restaurantMenuItem.isLactoseFreeItem())
                .menuId(restaurantMenuItem.getRestaurantMenu().getId())
                .build();
    }

    public MenuItem menuItemDTOToMenuItem (MenuItemDTO restaurantMenuItemDTO) throws Exception {
        return MenuItem
                .builder()
                .id(restaurantMenuItemDTO.getId())
                .itemName(restaurantMenuItemDTO.getItemName())
                .itemDescription(restaurantMenuItemDTO.getItemDescription())
                .itemPrice(restaurantMenuItemDTO.getItemPrice())
                .categoryFood(restaurantMenuItemDTO.getCategoryFood())
                .availability(restaurantMenuItemDTO.isAvailability())
                .vegetarianItem(restaurantMenuItemDTO.isVegetarianItem())
                .glutenFreeItem(restaurantMenuItemDTO.isGlutenFreeItem())
                .lactoseFreeItem(restaurantMenuItemDTO.isLactoseFreeItem())
                .restaurantMenu(restaurantMenuRepository.findRestaurantMenuById(restaurantMenuItemDTO.getMenuId())
                        .orElseThrow(() -> new Exception("No menu found")))
                .build();
    }
}
