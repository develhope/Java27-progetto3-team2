package com.develhope.Java27_progetto3_team2.restaurant.service;

import com.develhope.Java27_progetto3_team2.restaurant.dto.RestaurantDTO;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import com.develhope.Java27_progetto3_team2.restaurant.utils.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRestaurants() {
        Pageable pageable = PageRequest.of(0, 10);
        Restaurant restaurant = new Restaurant();
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        Page<Restaurant> restaurantPage = new PageImpl<>(Collections.singletonList(restaurant));

        when(restaurantRepository.findAll(pageable)).thenReturn(restaurantPage);
        when(restaurantMapper.toDTO(restaurant)).thenReturn(restaurantDTO);

        Page<RestaurantDTO> result = restaurantService.getAllRestaurants(0, 10);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testGetRestaurantById() throws Exception {
        Long id = 1L;
        Restaurant restaurant = new Restaurant();
        RestaurantDTO restaurantDTO = new RestaurantDTO();

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));
        when(restaurantMapper.toDTO(restaurant)).thenReturn(restaurantDTO);

        RestaurantDTO result = restaurantService.getRestaurantById(id);

        assertNotNull(result);
    }

    @Test
    void testGetRestaurantByIdNotFound() {
        Long id = 1L;

        when(restaurantRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(Exception.class, () -> {
            restaurantService.getRestaurantById(id);
        });

        assertEquals("Restaurant with id: " + id + " not found!", exception.getMessage());
    }

    @Test
    void testGetRestaurantByCategory() {
        String category = "Italian";
        Restaurant restaurant = new Restaurant();
        RestaurantDTO restaurantDTO = new RestaurantDTO();
        List<Restaurant> restaurants = Collections.singletonList(restaurant);

        when(restaurantRepository.findByCategory(category)).thenReturn(restaurants);
        when(restaurantMapper.toDTO(restaurant)).thenReturn(restaurantDTO);

        List<RestaurantDTO> result = restaurantService.getRestaurantByCategory(category);

        assertEquals(1, result.size());
    }
}