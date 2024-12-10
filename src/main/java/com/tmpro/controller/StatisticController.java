package com.tmpro.controller;

import com.tmpro.model.Player;
import com.tmpro.model.PlayerDTO;
import com.tmpro.model.Statistic;
import com.tmpro.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/statistics")
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    // Crear una nueva estadística
    @PostMapping
    public ResponseEntity<Statistic> createStatistic(@RequestBody Statistic statistic) {
        Statistic createdStatistic = statisticService.createStatistic(statistic);
        return ResponseEntity.ok(createdStatistic);
    }

    // Obtener estadísticas
    @GetMapping
    public ResponseEntity<List<Statistic>> getAllPlayers() {

        List<Statistic> statistic = statisticService.getAllStatistic();

        return ResponseEntity.ok(statistic);
    }

    // Obtener estadística por ID
    @GetMapping("/{id}")
    public ResponseEntity<Statistic> getStatisticById(@PathVariable Long id) {
        Optional<Statistic> statistic = statisticService.getStatisticById(id);
        return statistic.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener todas las estadísticas de un jugador
    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Statistic>> getStatisticsByPlayerId(@PathVariable Long playerId) {
        List<Statistic> statistics = statisticService.getStatisticsByPlayerId(playerId);
        return ResponseEntity.ok(statistics);
    }

    // Actualizar una estadística
    @PutMapping("/{id}")
    public ResponseEntity<Statistic> updateStatistic(@PathVariable Long id, @RequestBody Statistic updatedStatistic) {
        try {
            Statistic updated = statisticService.updateStatistic(id, updatedStatistic);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();  // Si no se encuentra la estadística
        }
    }

    // Eliminar una estadística
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatistic(@PathVariable Long id) {
        boolean isDeleted = statisticService.deleteStatistic(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
