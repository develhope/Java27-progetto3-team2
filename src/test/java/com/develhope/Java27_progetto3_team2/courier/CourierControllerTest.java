import com.develhope.Java27_progetto3_team2.courier.CourierController;
import com.develhope.Java27_progetto3_team2.courier.CourierDTO;
import com.develhope.Java27_progetto3_team2.courier.CourierService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CourierControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourierService courierService;

    @InjectMocks
    private CourierController courierController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(courierController).build();
    }

    @Test
    public void testAddNewCourier_Success() throws Exception {
        // Arrange
        CourierDTO courierDTO = new CourierDTO();
        courierDTO.setPhoneNumber("1234567890");
        courierDTO.setName("John Doe");

        CourierDTO savedCourier = new CourierDTO();
        savedCourier.setPhoneNumber("1234567890");
        savedCourier.setName("John Doe");

        when(courierService.addNewCourier(courierDTO)).thenReturn(savedCourier);

        // Act & Assert
        mockMvc.perform(post("/courier")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(courierDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"))
                .andExpect(jsonPath("$.name").value("John Doe"));

        verify(courierService, times(1)).addNewCourier(courierDTO);
    }

    @Test
    public void testAddNewCourier_Failure_PhoneNumberNull() throws Exception {
        // Arrange
        CourierDTO courierDTO = new CourierDTO(); // phoneNumber is null
        courierDTO.setName("John Doe");

        // Act & Assert
        mockMvc.perform(post("/courier")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(courierDTO)))
                .andExpect(status().isBadRequest());

        verify(courierService, never()).addNewCourier(any());
    }

    @Test
    public void testUpdateCourier_Success() throws Exception {
        // Arrange
        Long courierId = 1L;
        CourierDTO courierDTO = new CourierDTO();
        courierDTO.setPhoneNumber("0987654321");
        courierDTO.setName("Jane Doe");

        CourierDTO updatedCourier = new CourierDTO();
        updatedCourier.setPhoneNumber("0987654321");
        updatedCourier.setName("Jane Doe");

        when(courierService.updateCourier(courierId, courierDTO)).thenReturn(updatedCourier);

        // Act & Assert
        mockMvc.perform(patch("/courier/{idCourier}", courierId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(courierDTO)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.phoneNumber").value("0987654321"))
                .andExpect(jsonPath("$.name").value("Jane Doe"));

        verify(courierService, times(1)).updateCourier(courierId, courierDTO);
    }

    @Test
    public void testUpdateCourier_Failure() throws Exception {
        // Arrange
        Long courierId = 9L;
        CourierDTO courierDTO = new CourierDTO();
        courierDTO.setPhoneNumber("0987654321");
        courierDTO.setName("Jane Doe");

        when(courierService.updateCourier(courierId, courierDTO)).thenThrow(new Exception("Courier not found"));

        // Act & Assert
        mockMvc.perform(patch("/courier/{idCourier}", courierId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(courierDTO)))
                .andExpect(content().string(""));

        verify(courierService, times(9)).updateCourier(courierId, courierDTO);
    }
}
