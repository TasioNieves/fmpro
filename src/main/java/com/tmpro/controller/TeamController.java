package com.tmpro.controller;

import com.tmpro.model.Team;
import com.tmpro.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    // Obtener la lista de todos los equipos
    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        List<Team> teams = teamService.getAllTeams();
        return ResponseEntity.ok(teams);
    }

    // Obtener un equipo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Team team = teamService.getTeamById(id);
        if (team != null) {
            return ResponseEntity.ok(team);
        }
        return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra el equipo
    }

    // Crear un nuevo equipo
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Team team) {
        Team newTeam = teamService.saveTeam(team);
        return ResponseEntity.ok(newTeam);
    }

    // Actualizar los detalles de un equipo
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        Team updatedTeam = teamService.updateTeam(id, team);
        if (updatedTeam != null) {
            return ResponseEntity.ok(updatedTeam);
        }
        return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra el equipo
    }

    // Eliminar un equipo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        boolean isDeleted = teamService.deleteTeam(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build(); // Retorna 404 si no se encuentra el equipo
    }
}

