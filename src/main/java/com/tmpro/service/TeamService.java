package com.tmpro.service;

import com.tmpro.model.Team;
import com.tmpro.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    // Obtener todos los equipos
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    // Obtener un equipo por ID
    public Team getTeamById(Long id) {
        Optional<Team> teamOptional = teamRepository.findById(id);
        return teamOptional.orElse(null); // Retorna el equipo o null si no se encuentra
    }

    // Crear un nuevo equipo
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    // Actualizar un equipo existente
    public Team updateTeam(Long id, Team team) {
        if (teamRepository.existsById(id)) {
            team.setId(id); // Asegúrate de que el equipo tenga el ID correcto
            return teamRepository.save(team);
        }
        return null; // Retorna null si no se encuentra el equipo
    }

    // Eliminar un equipo
    public boolean deleteTeam(Long id) {
        if (teamRepository.existsById(id)) {
            teamRepository.deleteById(id);
            return true; // Retorna true si se eliminó con éxito
        }
        return false; // Retorna false si no se encuentra el equipo
    }
}
