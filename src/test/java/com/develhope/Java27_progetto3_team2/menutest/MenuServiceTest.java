package com.develhope.Java27_progetto3_team2.menutest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.develhope.Java27_progetto3_team2.menu.mapper.MenuItemMapper;
import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.menu.service.MenuService;
import com.develhope.Java27_progetto3_team2.restaurant.model.dto.RestaurantDTO;
import com.develhope.Java27_progetto3_team2.restaurant.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MenuServiceTest {

    @Mock
    private RestaurantService restaurantService;  // Mock for the restaurant service

    @Mock
    private MenuItemMapper menuItemMapper;  // Mock for the mapper

    @InjectMocks
    private MenuService menuService;  // The class containing the methods under test

    private RestaurantDTO mockRestaurantDTO;
    private RestaurantMenuDTO mockRestaurantMenuDTO;
    private MenuItem mockMenuItem;
    private MenuItemDTO mockMenuItemDTO;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Creating mocked objects
        mockRestaurantDTO = new RestaurantDTO();
        mockRestaurantMenuDTO = new RestaurantMenuDTO();
        mockMenuItem = new MenuItem();
        mockMenuItemDTO = new MenuItemDTO();

        List<MenuItem> menuItems = new ArrayList<>();
        menuItems.add(mockMenuItem);

        mockRestaurantMenuDTO.setMenuItemsList(menuItems);
        mockRestaurantDTO.setMenuRestaurant(mockRestaurantMenuDTO);

        // Defining mock behaviors
        when(restaurantService.getRestaurantById(anyLong())).thenReturn(mockRestaurantDTO);
        when(menuItemMapper.menuItemToMenuItemDTO(mockMenuItem)).thenReturn(mockMenuItemDTO);
    }

    @Test
    public void testGetRestaurantMenu() throws Exception {
        RestaurantMenuDTO result = menuService.getRestaurantMenu(1L);

        // Verify that restaurantService.getRestaurantById was called with the correct argument
        verify(restaurantService).getRestaurantById(1L);

        // Verify that the returned result is as expected
        assertEquals(mockRestaurantMenuDTO, result);
    }

    @Test
    public void testGetRestaurantMenuItem() throws Exception {
        List<MenuItemDTO> result = menuService.getRestaurantMenuItem(1L);

        // Verify that restaurantService.getRestaurantById was called
        verify(restaurantService).getRestaurantById(1L);

        // Verify that the mapper was called to convert MenuItem to MenuItemDTO
        verify(menuItemMapper).menuItemToMenuItemDTO(mockMenuItem);

        // Verify that the result contains the expected item
        assertEquals(1, result.size());
        assertEquals(mockMenuItemDTO, result.get(0));
    }
}
