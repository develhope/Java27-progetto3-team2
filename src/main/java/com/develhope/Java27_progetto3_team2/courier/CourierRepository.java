package com.develhope.Java27_progetto3_team2.courier;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    Courier findByEmail(String email);
}
