package com.tmpro.controller;

import com.tmpro.model.Player;
import com.tmpro.model.PlayerDTO;
import com.tmpro.model.Team;
import com.tmpro.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    // Crear un nuevo jugador
    @PostMapping
    public ResponseEntity<Object> createPlayer(@RequestBody PlayerDTO playerDTO) {
        try {
            // Intenta crear el jugador y devolverlo
            Player newPlayer = playerService.createPlayer(converter(playerDTO));
            return ResponseEntity.ok(newPlayer);  // Devuelve el jugador creado
        } catch (Exception e) {
            // Loguea la excepción para depuración
            e.printStackTrace();

            // Construye una respuesta personalizada con el error
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al crear el jugador");  // Mensaje claro para el usuario
            errorResponse.put("error", e.getMessage());  // Detalles técnicos para el desarrollador
            errorResponse.put("timestamp", LocalDateTime.now());  // Marca temporal del error

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



    private Player converter(PlayerDTO playerDTO){

        Player player = new Player();

        player.setName(playerDTO.getName());
        player.setDorsal(playerDTO.getDorsal());
        player.setPosition(playerDTO.getPosition());
        Team team = new Team();
        team.setId(Long.parseLong(playerDTO.getTeam_id()));
        player.setTeam(team);

        return player;
    };


    // Obtener todos los jugadores (opcional)
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);  // Devuelve la lista de jugadores
    }

    // Obtener un jugador por su ID (opcional)
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Optional<Player> player = playerService.findById(id);
        return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un jugador por su ID (opcional)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        if (playerService.deletePlayer(id)) {
            return ResponseEntity.noContent().build();  // El jugador se eliminó correctamente
        }
        return ResponseEntity.notFound().build();  // El jugador no se encontró
    }
}
