package com.develhope.Java27_progetto3_team2.order;

import com.develhope.Java27_progetto3_team2.restaurant.repository.RestaurantRepository;
import com.develhope.Java27_progetto3_team2.user.UserRepository;
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
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public OrderDTO addNewOrder(Long userId, Long restaurantId, OrderDTO orderDTO) throws Exception {
        Order order = orderMapper.mapperOrderDTOToOrder(orderDTO);
        order.setUser(userRepository.findById(userId).orElseThrow(() -> new Exception("User with id: " + userId + " not found!")));
        order.setRestaurant(restaurantRepository.findById(restaurantId).orElseThrow( () -> new Exception("Restaurant with id: " + restaurantId + " not found!")));
        order.setOrderDate(LocalDateTime.now());
        orderRepository.save(order);
        return orderMapper.mapperOrderToOrderDTO(order);
    }

    public OrderDTO updateOrder(OrderDTO orderDTO, Long idOrder) throws Exception {
        if(!(orderRepository.existsById(idOrder))){
            throw new Exception("Id doesn't exist!");
        }
       Order orderUpdated = orderRepository.save(orderMapper.mapperOrderDTOToOrder(orderDTO));
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
}
