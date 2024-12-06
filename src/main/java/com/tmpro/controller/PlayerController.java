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
            errorResponse.put("objeto", playerDTO);  // Marca temporal del error

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

    private PlayerDTO disconverter(Player player){

        PlayerDTO playerDTO = new PlayerDTO();

        playerDTO.setName(player.getName());
        playerDTO.setDorsal(player.getDorsal());
        playerDTO.setPosition(player.getPosition());
        playerDTO.setTeam_id(player.getTeam().getId()+"");

        return playerDTO;
    };

    private List<PlayerDTO> ConvertirLista (List<Player> players){

        List<PlayerDTO> listPlayer = new ArrayList<>();

        players.forEach(player -> {

            PlayerDTO playerDTO = disconverter(player);
            listPlayer.add(playerDTO);


        });

        return listPlayer;

    }

    // Obtener todos los jugadores (opcional)
    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {

        List<Player> players = playerService.getAllPlayers();

        return ResponseEntity.ok(ConvertirLista(players));  // Devuelve la lista de jugadores
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

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO playerDTO) {
        try {
            // Busca el jugador por ID (asegúrate de manejar el caso de que no exista)
           Optional<Player> existingPlayer = playerService.findById(id);

            if (existingPlayer == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("message", "Jugador no encontrado");
                errorResponse.put("timestamp", LocalDateTime.now());
                errorResponse.put("id", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }

            // Actualiza el jugador con los nuevos datos
            Optional<Player> updatedPlayer = playerService.updatePlayer(id, converter(playerDTO));
            return ResponseEntity.ok(updatedPlayer);  // Devuelve el jugador actualizado
        } catch (Exception e) {
            // Loguea la excepción para depuración
            e.printStackTrace();

            // Construye una respuesta personalizada con el error
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error al actualizar el jugador");
            errorResponse.put("error", e.getMessage());
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("objeto", playerDTO);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
