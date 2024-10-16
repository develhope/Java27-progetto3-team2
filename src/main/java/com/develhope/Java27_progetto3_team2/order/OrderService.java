package com.develhope.Java27_progetto3_team2.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    public OrderDTO addNewOrder(OrderDTO orderDTO) throws Exception {
        Order order = orderMapper.mapperOrderDTOToOrder(orderDTO);
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
}
