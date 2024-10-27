package com.develhope.Java27_progetto3_team2.ordertest;

import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.model.dto.MenuItemDTO;
import com.develhope.Java27_progetto3_team2.menu.repository.MenuItemRepository;
import com.develhope.Java27_progetto3_team2.order.*;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import com.develhope.Java27_progetto3_team2.user.User;
import com.develhope.Java27_progetto3_team2.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddNewOrder_Success() {
        Long userId = 1L;
        Long restaurantId = 1L;
        User user = new User();
        Restaurant restaurant = new Restaurant();

        // Creiamo un OrderDTO con i parametri corretti
        MenuItemDTO menuItemDTO = new MenuItemDTO(1L, "Pizza", "Pizza Margherita", 10.0, "Main Course", true, true, false, false, null);
        OrderDTO orderDTO = OrderDTO.builder()
                .items(Collections.singletonList(menuItemDTO))
                .build();

        Order order = new Order();
        order.setTotalPrice(10.0);

        MenuItem menuItem = new MenuItem();
        menuItem.setItemPrice(10.0);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(restaurantRepository.findById(restaurantId)).thenReturn(Optional.of(restaurant));
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        when(orderMapper.mapperOrderDTOToOrder(orderDTO)).thenReturn(order);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        when(orderMapper.mapperOrderToOrderDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.addNewOrder(userId, restaurantId, orderDTO);

        assertNotNull(result);
        assertEquals(orderDTO.getTotalPrice(), result.getTotalPrice());

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(menuItemRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddNewOrder_UserNotFound() {
        Long userId = 1L;
        Long restaurantId = 1L;
        OrderDTO orderDTO = new OrderDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.addNewOrder(userId, restaurantId, orderDTO));
    }

    @Test
    public void testUpdateOrder_Success() {
        Long orderId = 1L;
        MenuItemDTO menuItemDTO = new MenuItemDTO(1L, "Burger", "Beef Burger", 12.5, "Main Course", true, false, false, false, null);
        OrderDTO orderDTO = OrderDTO.builder()
                .deliveryAddress("New Address")
                .status(OrderStatus.PENDING)
                .items(Collections.singletonList(menuItemDTO))
                .build();

        // Creazione dell'ordine esistente con ID impostato tramite builder
        Order existingOrder = Order.builder()
                .id(orderId)
                .status(OrderStatus.PENDING)
                .build();

        MenuItem menuItem = new MenuItem();
        menuItem.setItemPrice(12.5);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
        when(orderRepository.save(any(Order.class))).thenReturn(existingOrder);
        when(orderMapper.mapperOrderToOrderDTO(any(Order.class))).thenReturn(orderDTO);

        OrderDTO result = orderService.updateOrder(orderDTO, orderId);

        assertNotNull(result);
        assertEquals(orderDTO.getDeliveryAddress(), result.getDeliveryAddress());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testUpdateOrder_OrderNotFound() {
        Long orderId = 1L;
        OrderDTO orderDTO = new OrderDTO();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> orderService.updateOrder(orderDTO, orderId));
    }

    @Test
    public void testDeleteOrder_Success() {
        Long orderId = 1L;
        when(orderRepository.existsById(orderId)).thenReturn(true);

        boolean result = orderService.deleteOrder(orderId);

        assertTrue(result);
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void testDeleteOrder_OrderNotFound() {
        Long orderId = 1L;
        when(orderRepository.existsById(orderId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> orderService.deleteOrder(orderId));
        verify(orderRepository, never()).deleteById(orderId);
    }

    @Test
    public void testGetOrdersByUserId_ReturnsOrders() {
        Long userId = 1L;

        Order order1 = Order.builder().id(1L).totalPrice(100.0).build();
        Order order2 = Order.builder().id(2L).totalPrice(200.0).build();
        List<Order> orderList = Arrays.asList(order1, order2);

        when(orderRepository.findByUserId(userId)).thenReturn(orderList);
        when(orderMapper.mapperOrderToOrderDTO(order1)).thenReturn(
                OrderDTO.builder().id(order1.getId()).totalPrice(order1.getTotalPrice()).build());
        when(orderMapper.mapperOrderToOrderDTO(order2)).thenReturn(
                OrderDTO.builder().id(order2.getId()).totalPrice(order2.getTotalPrice()).build());

        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);

        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testGetAllOrders() {
        Order order1 = Order.builder().id(1L).totalPrice(100.0).build();
        Order order2 = Order.builder().id(2L).totalPrice(200.0).build();
        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findAll()).thenReturn(orders);
        when(orderMapper.fromOrderListToDTOList(orders)).thenReturn(Arrays.asList(
                OrderDTO.builder().id(order1.getId()).totalPrice(order1.getTotalPrice()).build(),
                OrderDTO.builder().id(order2.getId()).totalPrice(order2.getTotalPrice()).build()
        ));

        List<OrderDTO> result = orderService.getAllOrder();

        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findAll();
    }
}