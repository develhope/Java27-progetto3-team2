package com.develhope.Java27_progetto3_team2.courier;

import com.develhope.Java27_progetto3_team2.exception.exceptions.CourierAlreadyExistException;
import com.develhope.Java27_progetto3_team2.exception.exceptions.EntityNotFoundException;
import com.develhope.Java27_progetto3_team2.order.entity.Order;
import com.develhope.Java27_progetto3_team2.order.repository.OrderRepository;
import com.develhope.Java27_progetto3_team2.user.Role;
import com.develhope.Java27_progetto3_team2.user.User;
import com.develhope.Java27_progetto3_team2.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CourierServiceTest {

    @Mock
    private CourierMapper courierMapper;
    @Mock
    private CourierRepository courierRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CourierService courierService;

    private Courier courier, courier2;
    private CourierDTO courierDTO, courierDTO2;
    private Order order;
    private UserDetails userDetails;
    User user = (User) userDetails;

    @BeforeEach
    void setUp(){
        courier = Courier.builder()
                .name("Mario")
                .email("courier@gmail.com")
                .phoneNumber("321318484")
                .surname("Rossi")
                .orderList(new ArrayList<>())
                .id(1L).build();

        courier2 = Courier.builder()
                .name("Mario")
                .email("courier@gmail.com")
                .phoneNumber("321318484")
                .surname("Rossi")
                .orderList(null).build();

        courierDTO = CourierDTO.builder()
                .name("Mario")
                .email("courier@gmail.com")
                .phoneNumber("321318484")
                .surname("Rossi")
                .id(1L)
                .orderList(new ArrayList<>()).build();

        courierDTO2 = CourierDTO.builder()
                .name("Mario")
                .email("courier@gmail.com")
                .phoneNumber("321318484")
                .surname("Rossi")
                .orderList(null).build();

        user = User.builder()
                .email("courier@gmail.com")
                .name("Mario")
                .surname("Rossi")
                .phoneNumber("321318484")
                .build();

        order = Order.builder()
                .id(1L)
                .build();

        userDetails = user;
    }

    @Test
    void addNewCourier_WhenOrderListIsNotNull_CreateNewCourier() {
        when(courierMapper.toCourier(courierDTO)).thenReturn(courier);
        when(courierMapper.toCourierDTO(courier)).thenReturn(courierDTO);

        CourierDTO result = courierService.addNewCourier(courierDTO);

        assertNotNull(result.getOrderList(),"Order list should be initialized if null");

        verify(courierMapper, times(1)).toCourier(courierDTO);
        verify(courierRepository,times(1)).save(courier);
        verify(courierMapper, times(1)).toCourierDTO(courier);
    }

    @Test
    void addNewCourier_WhenOrderListIsNull_CreateOrderList() {
        courierService.addNewCourier(courierDTO);

        assertNotNull(courierDTO.getOrderList(),"Order list should be initialized if null");
        assertTrue(courierDTO.getOrderList().isEmpty(),"Order list should be empty after initialization");
    }

    @Test
    void updateCourier_WhenIdCourierFound() throws Exception {
        Long idCourier = 1L;

        when(courierRepository.existsById(idCourier)).thenReturn(true);
        when(courierMapper.toCourier(courierDTO)).thenReturn(courier);
        when(courierMapper.toCourierDTO(courier)).thenReturn(courierDTO);

        CourierDTO result = courierService.updateCourier(idCourier,courierDTO);

        assertNotNull(result);
        assertEquals(courierDTO.getName(),result.getName());
        assertEquals(courierDTO.getEmail(),result.getEmail());

        verify(courierRepository, times(1)).save(courier);
    }

    @Test
    void updateCourier_WhenIdCourierNonFound_ThrowException() throws Exception {
        Long idCourier = 1L;

        when(courierRepository.existsById(idCourier)).thenReturn(false);

        Exception exception = assertThrows(Exception.class, () -> {
            courierService.updateCourier(idCourier,courierDTO);
        });
        assertEquals("Courier not found",exception.getMessage());

        verify(courierRepository, never()).save(any(Courier.class));
    }

    @Test
    void deleteOrder_WhenIdCourierExists_ShouldReturnTrue() {
        Long idCourier = 1L;

        when(courierRepository.existsById(idCourier)).thenReturn(true);

        boolean result = courierService.deleteOrder(idCourier);

        assertTrue(result,"The method should return true if the courier exists and is deleted");

        verify(courierRepository, times(1)).deleteById(idCourier);
    }

    @Test
    void deleteOrder_WhenIdCourierDoesNotExist_ShouldReturnFalse(){
        Long idCourier = 1L;

        when(courierRepository.existsById(idCourier)).thenReturn(false);

        boolean result = courierService.deleteOrder(idCourier);

        assertFalse(result,"The method should return false if the courier does not exist");

        verify(courierRepository, times(1)).deleteById(idCourier);
    }

    @Test
    void getCourierById_WhenIdExists_ShouldReturnCourierDTO() throws Exception {
        Long idCourier = 1L;

        when(courierRepository.getReferenceById(idCourier)).thenReturn(courier);
        when(courierMapper.toCourierDTO(courier)).thenReturn(courierDTO);

        CourierDTO result = courierService.getCourierById(idCourier);

        assertNotNull(result,"The method should return a CourierDTO if the ID exists");
        assertEquals(courierDTO.getId(),result.getId());
        assertEquals(courierDTO.getName(),result.getName());
        assertEquals(courierDTO.getEmail(),result.getEmail());
    }

    @Test
    void getCourierById_WhenIdDoesNotExist_ShouldThrowException() {
        Long idCourier = 1L;

        when(courierRepository.getReferenceById(idCourier)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            courierService.getCourierById(idCourier);
        });

        assertEquals("Courier not found by id: " + idCourier, exception.getMessage());
    }

    @Test
    void getCourierByEmail_WhenEmailExists_ShouldReturnCourierDTO() throws Exception {
        String emailCourier = "courier@gmail.com";

        when(courierRepository.findByEmail(emailCourier)).thenReturn(courier);
        when(courierMapper.toCourierDTO(courier)).thenReturn(courierDTO);

        CourierDTO result = courierService.getCourierByEmail(emailCourier);

        assertNotNull(result, "The method should return a CourierDTO if the email exists");
        assertEquals(courierDTO.getEmail(), result.getEmail());
        assertEquals(courierDTO.getName(), result.getName());
        assertEquals(courierDTO.getId(), result.getId());
    }

    @Test
    void getCourierByEmail_WhenEmailDoesNotExist_ShouldThrowException() {
        String emailCourier = "nonexistent@gmail.com";

        when(courierRepository.findByEmail(emailCourier)).thenReturn(null);

        Exception exception = assertThrows(Exception.class, () -> {
            courierService.getCourierByEmail(emailCourier);
        });
        assertEquals("Courier not found by email: " + emailCourier, exception.getMessage());
    }

    @Test
    void applyAsCourier_WhenEmailDoesNotExist_ShouldCreateAndReturnCourierDTO() {

        //when(userDetails.getUsername()).thenReturn(user.getEmail());
        when(courierRepository.existsByEmail(user.getEmail())).thenReturn(false);

        CourierDTO expectedCourierDTO = CourierDTO.builder()
                .email(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .status(CourierStatus.APPLICATION_PENDING)
                .orderList(new ArrayList<>())
                .phoneNumber(user.getPhoneNumber())
                .build();

        Courier expectedCourier = Courier.builder()
                .email(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .status(CourierStatus.APPLICATION_PENDING)
                .orderList(new ArrayList<>())
                .phoneNumber(user.getPhoneNumber())
                .build();

        courierRepository.save(expectedCourier);

        CourierDTO result = courierService.applyAsCourier(userDetails);

        assertNotNull(result, "The method shoul return a new CourierDTO");
        assertEquals(expectedCourierDTO.getEmail(),result.getEmail());
        assertEquals(expectedCourierDTO.getName(),result.getName());
        assertEquals(expectedCourierDTO.getStatus(),result.getStatus());

        verify(courierRepository, times(1)).save(any(Courier.class));
    }

    @Test
    void applyAsCourier_WhenEmailExists_ShouldThrowCourierAlreadyExistException() {
        when(courierRepository.existsByEmail(user.getEmail())).thenReturn(true);

        Exception exception = assertThrows(CourierAlreadyExistException.class, () -> {
            courierService.applyAsCourier(user);
        });
        assertEquals("Courier with email: " + user.getEmail() + " already exists", exception.getMessage());

        verify(courierRepository, never()).save(any(Courier.class));
    }

    @Test
    void changeApplicationStatus_WhenIdExistsAndStatusIsNotAccepted_ShouldUpdateStatusOnly() {
        String newStatus = "REJECTED";

        when(courierRepository.findById(courier.getId())).thenReturn(java.util.Optional.of(courier));
        when(courierMapper.toCourierDTO(courier)).thenReturn(new CourierDTO());

        CourierDTO result = courierService.changeApplicationStatus(courier.getId(), newStatus);

        assertEquals(CourierStatus.REJECTED, courier.getStatus());
        verify(userRepository, never()).findByEmail(anyString());
        verify(courierRepository, times(1)).save(courier);
        assertNotNull(result);
    }

    @Test
    void changeApplicationStatus_WhenIdExistsAndStatusIsAccepted_ShouldUpdateStatusAndUserRole() {
        String newStatus = "ACCEPTED";
        user.setEmail(courier.getEmail());

        when(courierRepository.findById(courier.getId())).thenReturn(java.util.Optional.of(courier));
        when(userRepository.findByEmail(courier.getEmail())).thenReturn(java.util.Optional.of(user));
        when(courierMapper.toCourierDTO(courier)).thenReturn(new CourierDTO());

        CourierDTO result = courierService.changeApplicationStatus(courier.getId(), newStatus);

        assertEquals(CourierStatus.ACCEPTED, courier.getStatus());
        assertEquals(Role.ROLE_COURIER, user.getRole());
        verify(userRepository, times(1)).findByEmail(courier.getEmail());
        verify(courierRepository, times(1)).save(courier);
        assertNotNull(result);
    }

    @Test
    void changeApplicationStatus_WhenIdDoesNotExist_ShouldThrowEntityNotFoundException() {
        Long idCourier = 99L;

        when(courierRepository.findById(idCourier)).thenReturn(java.util.Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            courierService.changeApplicationStatus(idCourier, "REJECTED");
        });
        assertEquals("Coruer with id: " + idCourier + " not found", exception.getMessage());
    }

    @Test
    void changeApplicationStatus_WhenStatusIsAcceptedAndUserNotFound_ShouldThrowEntityNotFoundException() {
        String newStatus = "ACCEPTED";

        when(courierRepository.findById(courier.getId())).thenReturn(java.util.Optional.of(courier));
        when(userRepository.findByEmail(courier.getEmail())).thenReturn(java.util.Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            courierService.changeApplicationStatus(courier.getId(), newStatus);
        });
        assertEquals("Coruer with email: " + courier.getEmail() + " not found", exception.getMessage());
    }

    @Test
    void addOrderToCourier_whenOrderSuccessfullyAssignedToTheCourier() {
        when(courierRepository.findByEmail("courier@gmail.com")).thenReturn(courier);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(courierMapper.toCourierDTO(courier)).thenReturn(courierDTO);

        CourierDTO result = courierService.addOrderToCourier(user, 1L);

        assertEquals(1, courier.getOrderList().size());
        assertEquals(order, courier.getOrderList().get(0));

        assertEquals(courierDTO, result);

        verify(courierRepository).findByEmail("courier@gmail.com");
        verify(orderRepository).findById(1L);
        verify(courierRepository).save(courier);
    }
}
