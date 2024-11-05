package com.develhope.Java27_progetto3_team2.order;



import com.develhope.Java27_progetto3_team2.cart.cart.repository.CartRepository;
import com.develhope.Java27_progetto3_team2.exception.exceptions.EntityNotFoundException;
import com.develhope.Java27_progetto3_team2.exception.exceptions.InvalidRequestException;
import com.develhope.Java27_progetto3_team2.cart.cart.model.Cart;
import com.develhope.Java27_progetto3_team2.cart.cartItem.model.CartItem;
import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.repository.MenuItemRepository;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import com.develhope.Java27_progetto3_team2.user.User;
import com.develhope.Java27_progetto3_team2.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final CartRepository cartRepository;

    // Metodo per calcolare il prezzo totale degli items
    private double calculateTotalPrice(List<MenuItem> menuItems) {
        return menuItems.stream().mapToDouble(MenuItem::getItemPrice).sum();
    }
    private double calculateTotalPriceCartItem(List<CartItem> cartItems) {
        double totalPrice = 0;
        for(CartItem cartItem : cartItems){
        totalPrice += cartItem.getMenuItem().getItemPrice() * cartItem.getQuantity();
    }
        return totalPrice;
    }

    public OrderDTO addNewOrder(Long userId, Long restaurantId, OrderDTO orderDTO) {
        if (userId <= 0 || restaurantId <= 0) {
            throw new InvalidRequestException("User ID and Restaurant ID must be positive.");
        }

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

        if (menuItems.isEmpty()) { // Controllo che l'ordine abbia almeno un item
            throw new InvalidRequestException("Order must contain at least one menu item.");
        }

        order.setItems(menuItems);
        order.setTotalPrice(calculateTotalPrice(menuItems));
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        return orderMapper.mapperOrderToOrderDTO(savedOrder);

    }

    @Transactional
    public OrderDTO addNewOrderFromCart(Cart cart, String address, String pay) {
        if (cart.getUser().getId() <= 0 || cart.getRestaurant().getId() <= 0) {
            throw new InvalidRequestException("User ID and Restaurant ID must be positive.");
        }

        // Creazione di un nuovo ordine
        Order order = new Order(1L, OrderStatus.PENDING, address, pay, LocalDateTime.now(),
                calculateTotalPriceCartItem(cart.getCartItemList()), null,
                cart.getUser(), cart.getRestaurant(), null, cart);

        // Salvataggio dell'ordine
        Order savedOrder = orderRepository.save(order);

        // Eliminazione del carrello
        cartRepository.delete(cart);

        // Ritorno dell'ordine come DTO
        return orderMapper.mapperOrderToOrderDTO(savedOrder);
    }

    public OrderDTO updateOrder(OrderDTO orderDTO, Long idOrder) {
        Order existingOrder = orderRepository.findById(idOrder)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + idOrder + " not found"));

        if (existingOrder.getStatus() == OrderStatus.COMPLETED) {
            throw new InvalidRequestException("Cannot modify a completed order.");
        }

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

        Order existingOrder = orderRepository.findById(idOrder)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + idOrder + " not found"));

        if (existingOrder.getStatus() == OrderStatus.COMPLETED) {
            throw new InvalidRequestException("Cannot delete a completed order.");
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

    public List<Order> ListIdforListOrder(List<Long> orderIdList) {
        List<Order> orderList = new ArrayList<>();
        if(orderIdList.isEmpty()){
            return orderList;
        }
        orderIdList.forEach(a->orderList.add(orderRepository.getReferenceById(a)));
        return orderList;
    }

    public List<Long> ListOrderforListIdOrder(List<Order> orderList) {
        List<Long> orderIdList = new ArrayList<>();
        if(orderList.isEmpty()){
            return orderIdList;
        }
        orderList.forEach(a->orderIdList.add(a.getId()));
        return orderIdList;
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.getReferenceById(orderId);
    }
}
