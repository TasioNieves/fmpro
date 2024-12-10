package com.tmpro.model;

import com.tmpro.model.Statistic;

public class StatisticDTO {

    private Long id;
    private Long playerId;
    private String match;
    private int goals;
    private int assists;
    private int minutesPlayed;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(int minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }

    public StatisticDTO(Statistic statistic) {
        this.id = statistic.getId();
        this.playerId = statistic.getPlayer().getId(); // Solo el ID del jugador
        this.match = statistic.getMatch();
        this.goals = statistic.getGoals();
        this.assists = statistic.getAssists();
        this.minutesPlayed = statistic.getMinutesPlayed();
    }
}
