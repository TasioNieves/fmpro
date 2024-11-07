package com.tmpro.controller;

import com.tmpro.model.Match;
import com.tmpro.service.MatchService;
import com.tmpro.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private PlayerService playerService; // Inyectar el servicio de jugadores

    // Obtener la lista de partidos
    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchService.findAllMatches();
        return ResponseEntity.ok(matches);
    }

    // Crear una convocatoria de partido
    @PostMapping
    public ResponseEntity<Match> createMatch(@RequestBody Match match) {
        Match newMatch = matchService.saveMatch(match);
        return ResponseEntity.ok(newMatch);
    }

    // Actualizar la alineación de un partido
    @PutMapping("/{id}/lineup")
    public ResponseEntity<Match> updateLineup(@PathVariable Long id, @RequestBody List<String> playerNames) {
        // Obtener IDs de jugadores a partir de los nombres
        List<Long> playerIds = playerNames.stream()
                .map(name -> playerService.findPlayerIdByName(name)) // Buscar el ID del jugador por nombre
                .collect(Collectors.toList());

        // Actualizar la alineación del partido
        Optional<Match> updatedMatchOptional = matchService.updateLineup(id, playerIds);

        // Comprobar si se encontró el partido y devolver la respuesta adecuada
        if (updatedMatchOptional.isPresent()) {
            return ResponseEntity.ok(updatedMatchOptional.get());
        } else {
            return ResponseEntity.notFound().build(); // Devolver 404 si no se encuentra el partido
        }
    }

    // Eliminar un partido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }
}
