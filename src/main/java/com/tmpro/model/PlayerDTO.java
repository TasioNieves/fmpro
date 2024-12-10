package com.tmpro.model;

import jakarta.persistence.*;
import java.util.List;


public class PlayerDTO {

    private Long id;

    private String name;  // Clave primaria

    private String position;
    private String dorsal;




    private String team_id;


    // Constructor sin argumentos
    public PlayerDTO() {}

    // Constructor que acepta un objeto Player
    public PlayerDTO(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.position = player.getPosition();
    }
    // Getters y Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDorsal() {
        return dorsal;
    }

    public void setDorsal(String dorsal) {
        this.dorsal = dorsal;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }


}
