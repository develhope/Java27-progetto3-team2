package com.develhope.Java27_progetto3_team2.menu.repository;

import com.develhope.Java27_progetto3_team2.menu.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
