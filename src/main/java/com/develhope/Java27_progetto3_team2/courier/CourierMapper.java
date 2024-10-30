package com.develhope.Java27_progetto3_team2.courier;

import com.develhope.Java27_progetto3_team2.order.Order;
import com.develhope.Java27_progetto3_team2.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CourierMapper {
    private final OrderService orderService;
    public Courier toCourier(CourierDTO courierDTO) {
        List<Order> orderList = orderService.ListIdforListOrder(courierDTO.getOrderList());

        return Courier.builder().name(courierDTO.getName()).email(courierDTO.getEmail()).phoneNumber(courierDTO.getPhoneNumber()).surname(courierDTO.getSurname()).status(courierDTO.getStatus()).orderList(orderList).id(courierDTO.getId()).build();
    }

    public CourierDTO toCourierDTO(Courier courier) {
        List<Long> orderIdList = orderService.ListOrderforListIdOrder(courier.getOrderList());

        return CourierDTO.builder().name(courier.getName()).email(courier.getEmail()).phoneNumber(courier.getPhoneNumber()).surname(courier.getSurname()).status(courier.getStatus()).orderList(orderIdList).id(courier.getId()).build();
    }
}
