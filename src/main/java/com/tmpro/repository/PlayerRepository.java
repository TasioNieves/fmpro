package com.tmpro.repository;

import com.tmpro.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    // Buscar un jugador por nombre (esto devuelve un Optional<Player>)
    Optional<Player> findByName(String name);

    // Buscar el ID de un jugador por su nombre (esto devuelve un Optional<Long>)
    Optional<Long> findIdByName(String name);
}
