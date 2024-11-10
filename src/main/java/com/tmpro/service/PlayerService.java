package com.tmpro.service;

import com.tmpro.model.Player;
import com.tmpro.model.Statistic;
import com.tmpro.repository.PlayerRepository;
import com.tmpro.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final StatisticRepository statisticRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, StatisticRepository statisticRepository) {
        this.playerRepository = playerRepository;
        this.statisticRepository = statisticRepository;
    }

    // Obtener todos los jugadores
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    // Buscar jugador por nombre
    public Optional<Player> findByName(String name) {
        return playerRepository.findByName(name);
    }

    // Buscar jugador por ID
    public Optional<Player> findById(Long id) {
        return playerRepository.findById(id);
    }

    // Crear un nuevo jugador
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    // Actualizar un jugador existente
    public Optional<Player> updatePlayer(Long id, Player updatedPlayer) {
        return playerRepository.findById(id).map(existingPlayer -> {
            existingPlayer.setName(updatedPlayer.getName());
            existingPlayer.setPosition(updatedPlayer.getPosition());
            existingPlayer.setDorsal(updatedPlayer.getDorsal());
            existingPlayer.setTeam(updatedPlayer.getTeam());
            return playerRepository.save(existingPlayer);
        });
    }

    // Eliminar un jugador
    public boolean deletePlayer(Long id) {
        return playerRepository.findById(id).map(player -> {
            playerRepository.delete(player);
            return true;
        }).orElse(false);
    }

    // Obtener estadísticas de un jugador específico
    public List<Statistic> getPlayerStatistics(Long id) {
        return playerRepository.findById(id)
                .map(player -> statisticRepository.findByPlayerId(player.getId()))
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado"));
    }

    // Obtener el ID de un jugador por su nombre
    public Long findPlayerIdByName(String name) {
        return playerRepository.findIdByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Jugador no encontrado"));
    }
}
