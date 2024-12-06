package com.tmpro.controller;

import com.tmpro.model.Statistic;
import com.tmpro.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/statistics")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    // Crear una nueva estadística
    @PostMapping
    public ResponseEntity<Statistic> createStatistic(@RequestBody Statistic statistic) {
        try {
            Statistic newStatistic = statisticService.createStatistic(statistic);
            return new ResponseEntity<>(newStatistic, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener estadística por ID
    @GetMapping("/{id}")
    public ResponseEntity<Statistic> getStatisticById(@PathVariable("id") Long id) {
        Optional<Statistic> statisticData = statisticService.getStatisticById(id);

        return statisticData.map(statistic -> new ResponseEntity<>(statistic, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener todas las estadísticas de un jugador
    @GetMapping("/player/{playerId}")
    public ResponseEntity<List<Statistic>> getStatisticsByPlayerId(@PathVariable("playerId") Long playerId) {
        try {
            List<Statistic> statistics = statisticService.getStatisticsByPlayerId(playerId);
            if (statistics.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(statistics, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar una estadística
    @PutMapping("/{id}")
    public ResponseEntity<Statistic> updateStatistic(@PathVariable("id") Long id, @RequestBody Statistic statistic) {
        Optional<Statistic> statisticData = statisticService.getStatisticById(id);

        if (statisticData.isPresent()) {
            Statistic updatedStatistic = statisticService.updateStatistic(id, statistic);
            return new ResponseEntity<>(updatedStatistic, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Eliminar una estadística
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStatistic(@PathVariable("id") Long id) {
        try {
            if (statisticService.deleteStatistic(id)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
