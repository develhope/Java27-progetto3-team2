package com.develhope.Java27_progetto3_team2.courier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
    boolean existsByEmail(String email);
    Courier findByEmail(String email);

}
