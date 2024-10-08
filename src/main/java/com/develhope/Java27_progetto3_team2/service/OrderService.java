package com.develhope.Java27_progetto3_team2.service;

import com.develhope.Java27_progetto3_team2.entity.Order;
import com.develhope.Java27_progetto3_team2.mapper.OrderMapper;
import com.develhope.Java27_progetto3_team2.model.OrderDTO;
import com.develhope.Java27_progetto3_team2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderService {
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderRepository orderRepository;

    public OrderDTO updateOrder(OrderDTO orderDTO, Long idOrder) throws Exception {
        if(!(orderRepository.existsById(idOrder))){
            throw new Exception("Id doesn't exist!");
        }
       Order orderUpdated = orderRepository.save(orderMapper.mapperOrderDTOToOrder(orderDTO));
        return orderMapper.mapperOrderToOrderDTO(orderUpdated);
    }
}
