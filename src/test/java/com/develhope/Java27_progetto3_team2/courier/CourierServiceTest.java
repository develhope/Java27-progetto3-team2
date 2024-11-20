package com.develhope.Java27_progetto3_team2.courier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourierServiceTest {
    @InjectMocks
    private CourierService courierService;
    @Mock
    private CourierMapper courierMapper;
    @Mock
    private CourierRepository courierRepository;

    private Courier courier;
    private CourierDTO courierDTO;
    @BeforeEach
    void setUp() {
        courierDTO = CourierDTO.builder()
                .id(1L)
                .email("teste@teste.com")
                .name("Teste")
                .surname("Teste")
                .status("Available")
                .phoneNumber("1234567")
                .build();
        courier = Courier.builder()
                .id(1L)
                .email("teste@teste.com")
                .name("Teste")
                .surname("Teste")
                .status("Available")
                .phoneNumber("1234567")
                .build();
    }

    @Test
    void addNewCourier() {
        when(courierMapper.toCourier(courierDTO)).thenReturn(courier);
        when(courierMapper.toCourierDTO(courier)).thenReturn(courierDTO);
        when(courierRepository.save(courier)).thenReturn(courier);
        CourierDTO courierDTO1 = courierService.addNewCourier(courierDTO);

        assertEquals(courierDTO1.getId(),1L);
        assertEquals(courierDTO1.getName(),"Teste");
        assertEquals(courierDTO1.getPhoneNumber(),"1234567");
    }

    @Test
    void updateCourier_whenUserNotExists() {
        CourierDTO courierDTO1 = new CourierDTO();
        Exception exception = assertThrows(Exception.class, () -> courierService.updateCourier(3L,courierDTO1));

        assertEquals("Courier not found", exception.getMessage());
    }

/*
    @Test
    void updateCourier_whenAllIsOk() throws Exception {
        when(courierRepository.findById(anyLong())).thenReturn(Optional.of(courier));
        when(courierMapper.toCourierDTO(courier)).thenReturn(courierDTO);

        CourierDTO updated = courierService.updateCourier(1L,courierDTO);
        assertEquals("Teste", updated.getName());
    }


    @Test
    void getCourierById() throws Exception {
        Long id = courier.getId();
        when(courierRepository.findById(anyLong())).thenReturn(Optional.of(courier));
        when(courierMapper.toCourierDTO(courier)).thenReturn(courierDTO);

        CourierDTO found = courierService.getCourierById(id);
        assertEquals(courierDTO, found);
    }

 */

    @Test
    void getCourierByEmail() throws Exception {
        when(courierRepository.findByEmail("teste@test.com")).thenReturn(courier);
        when(courierMapper.toCourierDTO(courier)).thenReturn(courierDTO);

        CourierDTO found = courierService.getCourierByEmail("teste@test.com");
        assertEquals(courierDTO, found);
    }

    @Test
    void getCourierByEmail_whenNotExists() throws Exception {
        Exception exception = assertThrows(Exception.class, () -> courierService.getCourierByEmail("teste.teste@teste.com"));


        assertEquals("Courier not found by email: teste.teste@teste.com", exception.getMessage());
    }
}