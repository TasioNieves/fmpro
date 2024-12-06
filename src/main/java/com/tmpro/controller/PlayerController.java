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
import java.util.*;

@RestController
@RequestMapping("api/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    // Crear un nuevo jugador
    @PostMapping
    public ResponseEntity<Object> createPlayer(@RequestBody PlayerDTO playerDTO) {
        try {
            Player newPlayer = playerService.createPlayer(converter(playerDTO));
            return ResponseEntity.ok(newPlayer);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al crear el jugador");
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("objeto", playerDTO);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Obtener todos los jugadores
    @GetMapping
    public ResponseEntity<Object> getAllPlayers() {
        try {
            List<Player> players = playerService.getAllPlayers();
            return ResponseEntity.ok(ConvertirLista(players));
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al obtener los jugadores");
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Obtener un jugador por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        try {
            Optional<Player> player = playerService.findById(id);
            return player.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al obtener el jugador");
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Player) errorResponse);
        }
    }

    // Eliminar un jugador por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePlayer(@PathVariable Long id) {
        try {
            if (playerService.deletePlayer(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al eliminar el jugador");
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Actualizar un jugador por su ID
    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO playerDTO) {
        try {
            Optional<Player> updatedPlayer = playerService.updatePlayer(id, converter(playerDTO));
            return updatedPlayer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al actualizar el jugador");
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("objeto", playerDTO);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((Player) errorResponse);
        }
    }

    private Player converter(PlayerDTO playerDTO) {
        Player player = new Player();
        player.setName(playerDTO.getName());
        player.setDorsal(playerDTO.getDorsal());
        player.setPosition(playerDTO.getPosition());
        Team team = new Team();
        team.setId(Long.parseLong(playerDTO.getTeam_id()));
        player.setTeam(team);
        return player;
    }

    private PlayerDTO disconverter(Player player) {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName(player.getName());
        playerDTO.setDorsal(player.getDorsal());
        playerDTO.setPosition(player.getPosition());
        playerDTO.setTeam_id(player.getTeam().getId() + "");
        return playerDTO;
    }

    private List<PlayerDTO> ConvertirLista(List<Player> players) {
        List<PlayerDTO> listPlayer = new ArrayList<>();
        players.forEach(player -> {
            PlayerDTO playerDTO = disconverter(player);
            listPlayer.add(playerDTO);
        });
        return listPlayer;
    }
}
