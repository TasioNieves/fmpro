package com.tmpro.repository;

import com.tmpro.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    // Método personalizado para encontrar estadísticas por el ID del jugador
    List<Statistic> findByPlayerId(Long playerId);
}
