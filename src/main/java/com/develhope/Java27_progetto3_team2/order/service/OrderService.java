package com.develhope.Java27_progetto3_team2.order.service;


import com.develhope.Java27_progetto3_team2.cart.cart.model.Cart;
import com.develhope.Java27_progetto3_team2.cart.cartItem.model.CartItem;
import com.develhope.Java27_progetto3_team2.cart.mapper.CartMapper;
import com.develhope.Java27_progetto3_team2.email.EmailService;
import com.develhope.Java27_progetto3_team2.exception.exceptions.EntityNotFoundException;
import com.develhope.Java27_progetto3_team2.exception.exceptions.InvalidRequestException;
import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import com.develhope.Java27_progetto3_team2.menu.repository.MenuItemRepository;
import com.develhope.Java27_progetto3_team2.order.dto.OrderDTO;
import com.develhope.Java27_progetto3_team2.order.dto.UserOrderDTO;
import com.develhope.Java27_progetto3_team2.order.entity.Order;
import com.develhope.Java27_progetto3_team2.order.enumerator.OrderStatus;
import com.develhope.Java27_progetto3_team2.order.repository.OrderRepository;
import com.develhope.Java27_progetto3_team2.order.utils.OrderMapper;
import com.develhope.Java27_progetto3_team2.restaurant.model.Restaurant;
import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import com.develhope.Java27_progetto3_team2.user.User;
import com.develhope.Java27_progetto3_team2.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
    private final CartMapper cartMapper;
    private final EmailService emailService;

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

        //order.setItems(menuItems);
        order.setTotalPrice(calculateTotalPrice(menuItems));
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        return orderMapper.mapperOrderToOrderDTO(savedOrder);

    }


    @Transactional
    public UserOrderDTO addNewOrderFromCart(Cart cart, String address, String pay) {
        if (cart.getUser().getId() <= 0 || cart.getRestaurant().getId() <= 0) {
            throw new InvalidRequestException("User ID and Restaurant ID must be positive.");
        }

        Order order = new Order( OrderStatus.PENDING, address, pay, LocalDateTime.now(),
                calculateTotalPriceCartItem(cart.getCartItemList()), 1L,
                cart.getUser(), cart.getRestaurant(),cart.getCartItemList().stream().map(CartItem::getMenuItem).toList(), cart,LocalDateTime.now().plusMinutes(45));

        Order savedOrder = orderRepository.save(order);
        UserOrderDTO userOrderDTO = orderMapper.toUserOrderDTO(savedOrder);

        userOrderDTO.setCartDTO(cartMapper.cartToCartDTO(cart));
        userOrderDTO.setRestaurantName(cart.getRestaurant().getNameRestaurant());
        emailService.sendOrderConfirmationEmail(order);

        return userOrderDTO;
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
            //existingOrder.setItems(menuItems);
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

    /***
     * Change the order status based on order id and status
     * @param orderId The id of the order you want to modify
     * @param status Status: (PENDING,IN_PREPARATION,DELIVERING,COMPLETED)
     * @return returns the OrderDTO
     */
    public OrderDTO changeOrderStatus(Long orderId, String status){
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.valueOf(status));
        orderRepository.save(order);
        return orderMapper.mapperOrderToOrderDTO(order);
    }

    public UserOrderDTO getUserOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId + " not found"));
        UserOrderDTO userOrderDTO = orderMapper.toUserOrderDTO(order);

        Duration duration = Duration.between(LocalDateTime.now(), order.getDeliveryTime());
        long minutesRemaining = duration.toMinutes();
        long secondsRemaining = duration.minusMinutes(minutesRemaining).getSeconds();

        String timeRemaining = String.format("%02d:%02d", minutesRemaining, secondsRemaining);

        userOrderDTO.setArrivingTime(timeRemaining);

        return userOrderDTO;
    }


}
