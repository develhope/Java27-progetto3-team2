package com.develhope.Java27_progetto3_team2.repository;

import com.develhope.Java27_progetto3_team2.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
