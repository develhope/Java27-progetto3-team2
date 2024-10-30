package com.develhope.Java27_progetto3_team2.courier;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
}
