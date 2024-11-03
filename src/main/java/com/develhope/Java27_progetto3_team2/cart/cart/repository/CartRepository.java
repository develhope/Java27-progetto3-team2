package com.develhope.Java27_progetto3_team2.cart.cart.repository;

import com.develhope.Java27_progetto3_team2.cart.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser_Id(Long id);
}
