package com.develhope.Java27_progetto3_team2.order;

import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.repository.MenuItemRepository;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import com.develhope.Java27_progetto3_team2.user.User;
import com.develhope.Java27_progetto3_team2.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    // Metodo per calcolare il prezzo totale degli items
    private double calculateTotalPrice(List<MenuItem> menuItems) {
        return menuItems.stream().mapToDouble(MenuItem::getItemPrice).sum();
    }

    public OrderDTO addNewOrder(Long userId, Long restaurantId, OrderDTO orderDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant with id " + restaurantId + " not found"));

        Order order = orderMapper.mapperOrderDTOToOrder(orderDTO);
        order.setUser(user);
        order.setRestaurant(restaurant);

        List<MenuItem> menuItems = orderDTO.getItems().stream()
                .map(menuItemDTO -> menuItemRepository.findById(menuItemDTO.getId())
                        .orElseThrow(() -> new EntityNotFoundException("Menu item with id " + menuItemDTO.getId() + " not found")))
                .collect(Collectors.toList());

        order.setItems(menuItems);
        order.setTotalPrice(calculateTotalPrice(menuItems));
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        return orderMapper.mapperOrderToOrderDTO(savedOrder);
    }

    public OrderDTO updateOrder(OrderDTO orderDTO, Long idOrder) {
        Order existingOrder = orderRepository.findById(idOrder)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + idOrder + " not found"));

        // Aggiorno solo i campi modificabili
        existingOrder.setDeliveryAddress(orderDTO.getDeliveryAddress());
        existingOrder.setPaymentMethod(orderDTO.getPaymentMethod());
        existingOrder.setStatus(orderDTO.getStatus());

        if (orderDTO.getItems() != null && !orderDTO.getItems().isEmpty()) {
            List<MenuItem> menuItems = orderDTO.getItems().stream()
                    .map(menuItemDTO -> menuItemRepository.findById(menuItemDTO.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Menu item with id " + menuItemDTO.getId() + " not found")))
                    .collect(Collectors.toList());
            existingOrder.setItems(menuItems);
            existingOrder.setTotalPrice(calculateTotalPrice(menuItems));
        }

        Order updatedOrder = orderRepository.save(existingOrder);
        return orderMapper.mapperOrderToOrderDTO(updatedOrder);
    }

    public boolean deleteOrder(Long idOrder) {
        if (!orderRepository.existsById(idOrder)) {
            throw new EntityNotFoundException("Order with id " + idOrder + " not found");
        }
        orderRepository.deleteById(idOrder);
        return true;
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(orderMapper::mapperOrderToOrderDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrder() {
        return orderMapper.fromOrderListToDTOList(orderRepository.findAll());
    }
}
