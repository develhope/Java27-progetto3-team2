package com.develhope.Java27_progetto3_team2.courier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
    boolean existsByEmail(String email);
    Optional<Courier> findByEmail(String email);

}
