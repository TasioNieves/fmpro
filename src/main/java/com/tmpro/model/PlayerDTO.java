package com.tmpro.model;

import jakarta.persistence.*;
import java.util.List;


public class PlayerDTO {



    private String name;  // Clave primaria

    private String position;
    private String dorsal;




    private String team_id;



    // Getters y Setters




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
