package com.tmpro.service;

import com.tmpro.model.Statistic;
import com.tmpro.model.Player;
import com.tmpro.repository.StatisticRepository;
import com.tmpro.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatisticService {

    @Autowired
    private StatisticRepository statisticRepository;

    @Autowired
    private PlayerRepository playerRepository;

    // Crear una nueva estadística
    public Statistic createStatistic(Statistic statistic) {
        // Buscar al jugador usando el ID proporcionado
        Player player = playerRepository.findByName(statistic.getPlayer().getName())
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
        statistic.setPlayer(player);


        // Asignar el jugador a la estadística
        statistic.setPlayer(player);

        return statisticRepository.save(statistic);
    }

    // Obtener todas las estadísticas
    public List<Statistic> getAllStatistic() {
        return statisticRepository.findAll();
    }

    // Obtener estadística por ID
    public Optional<Statistic> getStatisticById(Long id) {
        return statisticRepository.findById(id);
    }

    // Obtener todas las estadísticas de un jugador
    public List<Statistic> getStatisticsByPlayerId(Long playerId) {
        return statisticRepository.findByPlayerId(playerId);
    }

    // Actualizar una estadística
    public Statistic updateStatistic(Long id, Statistic updatedStatistic) {
        // Verificar si la estadística existe
        return statisticRepository.findById(id)
                .map(statistic -> {
                    statistic.setGoals(updatedStatistic.getGoals());
                    statistic.setAssists(updatedStatistic.getAssists());
                    statistic.setMinutesPlayed(updatedStatistic.getMinutesPlayed());
                    statistic.setMatch(updatedStatistic.getMatch());
                    return statisticRepository.save(statistic);
                }).orElseThrow(() -> new RuntimeException("Estadística no encontrada"));
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
