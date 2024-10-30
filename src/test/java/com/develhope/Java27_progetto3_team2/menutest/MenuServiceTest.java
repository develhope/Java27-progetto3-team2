
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.develhope.Java27_progetto3_team2.menu.controller.MenuController;
import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.model.dto.RestaurantMenuDTO;
import com.develhope.Java27_progetto3_team2.menu.service.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class MenuServiceTest {

    @Mock
    private MenuService menuService; // Mock for the menu service

    @InjectMocks
    private MenuController menuController; // Controller under test

    private MockMvc mockMvc; // MockMvc to test the controller endpoints

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
    }

    @Test
    public void testGetRestaurantMenu() {
        RestaurantMenuDTO result = menuService.getRestaurantMenu(1L);


        // Perform the POST request and validate the response
        mockMvc.perform(post("/restaurantMenu/menu/{restaurantId}", restaurantId))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists()); // Check if response body exists

        // Verify that menuService.addMenuToRestaurant was called with the correct ID
        verify(menuService).addMenuToRestaurant(restaurantId);
    }

    @Test
    public void testGetRestaurantMenuItem() {
        List<MenuItemDTO> result = menuService.getRestaurantMenuItem(1L);


        // Simulate an exception being thrown by menuService
        when(menuService.addMenuToRestaurant(restaurantId)).thenThrow(new Exception("Error creating menu"));

        // Perform the POST request and validate the response
        mockMvc.perform(post("/restaurantMenu/menu/{restaurantId}", restaurantId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error creating menu")); // Check if error message matches

        // Verify that menuService.addMenuToRestaurant was called with the correct ID
        verify(menuService).addMenuToRestaurant(restaurantId);
    }
}