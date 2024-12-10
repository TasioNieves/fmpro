package com.tmpro.service;

import com.tmpro.model.Player;
import com.tmpro.model.Statistic;
import com.tmpro.repository.StatisticRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticService {

    private final StatisticRepository statisticRepository;

    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    // Crear una nueva estadística
    public Statistic createStatistic(Statistic statistic) {
        return statisticRepository.save(statistic);
    }

    // Obtener estadística por ID
    public Optional<Statistic> getStatisticById(Long id) {
        return statisticRepository.findById(id);
    }

    // Obtener todas las estadísticas de un jugador
    public List<Statistic> getStatisticsByPlayerId(Long playerId) {
        return statisticRepository.findByPlayerId(playerId);
    }
    // Obtener todos los jugadores
    public List<Statistic> getAllStatistic() {
        return statisticRepository.findAll();
    }

    // Actualizar una estadística
    public Statistic updateStatistic(Long id, Statistic updatedStatistic) {
        return statisticRepository.findById(id).map(statistic -> {
            statistic.setPlayer(updatedStatistic.getPlayer());
            statistic.setMatch(updatedStatistic.getMatch());
            statistic.setGoals(updatedStatistic.getGoals());
            statistic.setAssists(updatedStatistic.getAssists());
            statistic.setMinutesPlayed(updatedStatistic.getMinutesPlayed());
            return statisticRepository.save(statistic);
        }).orElseThrow(() -> new RuntimeException("Estadística no encontrada con ID: " + id));
    }

    // Eliminar una estadística
    public boolean deleteStatistic(Long id) {
        if (statisticRepository.existsById(id)) {
            statisticRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
