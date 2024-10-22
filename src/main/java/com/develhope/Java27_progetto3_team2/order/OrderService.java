package com.develhope.Java27_progetto3_team2.order;

import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.repository.MenuItemRepository;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import com.develhope.Java27_progetto3_team2.user.User;
import com.develhope.Java27_progetto3_team2.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public OrderDTO addNewOrder(OrderDTO orderDTO) throws Exception {

        User user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new Exception("User not found with id: " + orderDTO.getUserId()));

        Restaurant restaurant = restaurantRepository.findById(orderDTO.getRestaurantId())
                .orElseThrow(() -> new Exception("Restaurant not found with id: " + orderDTO.getRestaurantId()));

        Order order = orderMapper.mapperOrderDTOToOrder(orderDTO);
        order.setUser(user);  // Assegna l'utente all'ordine
        order.setRestaurant(restaurant);  // Assegna il ristorante all'ordine

        List<MenuItem> menuItems = orderDTO.getItems().stream()
                .map(menuItemDTO -> {
                    try {
                        return menuItemRepository
                                .findById(menuItemDTO.getId())
                                .orElseThrow(() -> new Exception("Menu item with id " + menuItemDTO.getId() + " not found"));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        order.setItems(menuItems);
        double totalPrice = menuItems.stream().mapToDouble(MenuItem::getItemPrice).sum();
        order.setTotalPrice(totalPrice);

        orderRepository.save(order);
        return orderMapper.mapperOrderToOrderDTO(order);
    }

    public OrderDTO updateOrder(OrderDTO orderDTO, Long idOrder) throws Exception {
        if(!(orderRepository.existsById(idOrder))){
            throw new Exception("Id doesn't exist!");
        }
        Order orderUpdated = orderMapper.mapperOrderDTOToOrder(orderDTO);

        List<MenuItem> menuItems = orderDTO.getItems().stream()
                .map(menuItemDTO -> menuItemRepository.findById(menuItemDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Menu item with id " + menuItemDTO.getId() + " not found")))
                .collect(Collectors.toList());
        orderUpdated.setItems(menuItems);

        double totalPrice = menuItems.stream().mapToDouble(MenuItem::getItemPrice).sum();
        orderUpdated.setTotalPrice(totalPrice);

        orderRepository.save(orderUpdated);
        return orderMapper.mapperOrderToOrderDTO(orderUpdated);
    }

    public boolean deleteOrder(Long idOrder) {
        if (!orderRepository.existsById(idOrder)) {
            return false; // L'ordine non esiste, restituisci false
        }
        orderRepository.deleteById(idOrder); // Elimina l'ordine
        return true; // Restituisci true per indicare che l'ordine è stato eliminato con successo
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(orderMapper::mapperOrderToOrderDTO).collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrder(){
        return orderMapper.fromOrderListToDTOList(orderRepository.findAll());
    }
}
