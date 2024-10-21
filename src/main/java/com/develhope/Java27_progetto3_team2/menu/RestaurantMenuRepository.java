package com.develhope.Java27_progetto3_team2.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantMenuRepository extends JpaRepository<RestaurantMenu, Long> {

    // Trova i menu di un ristorante per ID ristorante (utile ad esempio per operazioni batch)
    // List<RestaurantMenu> findByRestaurantId(Restaurant restaurantId);

    // Trova i menu di un ristorante per ID con paginazione
    // Page<RestaurantMenu> findByRestaurantId(Restaurant restaurantId, Pageable pageable);

    /*
    Idee per Query che potremmo implementare
    - Filtra piatti vegetariani e senza glutine
    - Filtra per disponibilità dei piatti
    - Filtra per fasce di prezzo
    - Filtra in base all'orario

    - Cache per migliorare le performance
    */
}

