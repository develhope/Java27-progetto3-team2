package com.develhope.Java27_progetto3_team2.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User,Long> {
    Optional<User> findByEmail(String email);

}
