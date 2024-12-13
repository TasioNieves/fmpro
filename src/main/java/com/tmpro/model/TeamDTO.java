package com.tmpro.model;


import jakarta.persistence.*;

import java.util.List;


public class TeamDTO {


    private Long id;

    private String name;
    private String coach; // Nombre del entrenador




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

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }


}
