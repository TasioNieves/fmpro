package com.tmpro.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date; // Cambiado a LocalDateTime para manejar fechas y horas
    private String location;

    @OneToMany(mappedBy = "match")
    private List<Statistic> statistics; // Relación con Statistic

    // Aquí se agrega la relación Many-to-Many con Player para la alineación (lineup)
    @ManyToMany
    @JoinTable(
            name = "match_lineup",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )

    private List<Player> lineup; // Lista de jugadores en la alineación

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Player> getLineup() {
        return lineup;
    }

    public void setLineup(List<Player> lineup) {
        this.lineup = lineup;
    }
}
