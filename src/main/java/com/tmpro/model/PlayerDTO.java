package com.tmpro.model;

import jakarta.persistence.*;
import java.util.List;


public class PlayerDTO {


    private Long id;
    private String name;  // Clave primaria

    private String position;
    private String dorsal;


    private int team_id;


    private List<Statistic> statistics; // Relación con Statistic

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

    public int getTeam() {
        return team_id;
    }

    public void setTeam(int team) {
        this.team_id = team_id;
    }

    public List<Statistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistic> statistics) {
        this.statistics = statistics;
    }
}
