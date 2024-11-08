package com.tmpro.model;


import jakarta.persistence.*;


@Entity
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    private int goals = 0;
    private int assists = 0;
    private int minutesPlayed = 0;

    // Constructor vacío
    public Statistic() {}

    // Constructor con parámetros
    public Statistic(Player player, Match match, int goals, int assists, int minutesPlayed) {
        this.player = player;
        this.match = match;
        this.goals = goals;
        this.assists = assists;
        this.minutesPlayed = minutesPlayed;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }

    public Match getMatch() { return match; }
    public void setMatch(Match match) { this.match = match; }

    public int getGoals() { return goals; }
    public void setGoals(int goals) { this.goals = goals; }

    public int getAssists() { return assists; }
    public void setAssists(int assists) { this.assists = assists; }

    public int getMinutesPlayed() { return minutesPlayed; }
    public void setMinutesPlayed(int minutesPlayed) { this.minutesPlayed = minutesPlayed; }
}
