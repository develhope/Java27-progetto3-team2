package com.develhope.Java27_progetto3_team2.ordertest;

import com.develhope.Java27_progetto3_team2.order.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class getOrdersByUserIdTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetOrdersByUserId_ReturnsOrders() {
        // Setup: creiamo un mock di UserID e degli ordini
        Long userId = 1L;

        // Simuliamo la lista di ordini
        Order order1 = Order.builder().id(1L).totalPrice(100.0).build();
        Order order2 = Order.builder().id(2L).totalPrice(200.0).build();
        List<Order> orderList = Arrays.asList(order1, order2);

        // Simuliamo il comportamento del repository e del mapper
        when(orderRepository.findByUserId(userId)).thenReturn(orderList);
        when(orderMapper.mapperOrderToOrderDTO(order1)).thenReturn(new OrderDTO(order1.getId(), null, null, null, null, order1.getTotalPrice(), null));
        when(orderMapper.mapperOrderToOrderDTO(order2)).thenReturn(new OrderDTO(order2.getId(), null, null, null, null, order2.getTotalPrice(), null));

        // Eseguiamo il metodo che vogliamo testare
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);

        // Verifichiamo i risultati
        assertEquals(2, orders.size()); // Deve restituire 2 ordini
        assertEquals(1L, orders.get(0).getId()); // Primo ordine ha ID 1
        assertEquals(100.0, orders.get(0).getTotalPrice()); // Prezzo totale del primo ordine è 100
        assertEquals(2L, orders.get(1).getId()); // Secondo ordine ha ID 2
        assertEquals(200.0, orders.get(1).getTotalPrice()); // Prezzo totale del secondo ordine è 200

        // Verifichiamo che i metodi del repository e del mapper siano stati chiamati correttamente
        verify(orderRepository, times(1)).findByUserId(userId);
        verify(orderMapper, times(1)).mapperOrderToOrderDTO(order1);
        verify(orderMapper, times(1)).mapperOrderToOrderDTO(order2);
    }

    @Test
    public void testGetOrdersByUserId_ReturnsEmptyList() {
        // Setup: UserID per il quale non ci sono ordini
        Long userId = 2L;

        // Simuliamo che non ci siano ordini per l'utente
        when(orderRepository.findByUserId(userId)).thenReturn(Arrays.asList());

        // Eseguiamo il metodo
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);

        // Verifichiamo i risultati
        assertTrue(orders.isEmpty()); // La lista deve essere vuota

        // Verifichiamo che il repository sia stato chiamato
        verify(orderRepository, times(1)).findByUserId(userId);
        verify(orderMapper, never()).mapperOrderToOrderDTO(any(Order.class)); // Il mapper non deve essere chiamato
    }


}



