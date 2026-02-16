package com.example.padelscore.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Match {
    @SerializedName(value = "matchId", alternate = {"match_code"})
    private String matchId;

    @SerializedName("team1")
    private String team1;

    @SerializedName("team2")
    private String team2;

    @SerializedName("score")
    private String score;

    @SerializedName("scheduled_time")
    private String scheduledTime;

    @SerializedName("startTime")
    private String startTime;

    @SerializedName("status")
    private String status;

    @SerializedName("category")
    private String category;

    @SerializedName("round_name")
    private String roundName;

    @SerializedName("court")
    private String court;

    @SerializedName(value = "tournament_id", alternate = {"tournament_slug"})
    private String tournamentId;

    @SerializedName("tournament_name")
    private String tournamentName;

    public Match() {
        // Constructor vacío requerido por Gson
    }

    public Match(String matchId, String team1, String team2, String score) {
        this.matchId = matchId;
        this.team1 = team1;
        this.team2 = team2;
        this.score = score;
    }

    public long getId() {
        try {
            return Long.parseLong(matchId);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public void setId(long id) {
        this.matchId = String.valueOf(id);
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(String tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getFecha() {
        return scheduledTime != null && !scheduledTime.trim().isEmpty()
                ? scheduledTime
                : startTime;
    }

    public void setFecha(String fecha) {
        this.startTime = fecha;
    }

    public String getEstado() {
        return status;
    }

    public void setEstado(String estado) {
        this.status = estado;
    }

    public List<String> getJugadores() {
        if (team1 == null && team2 == null) {
            return Collections.emptyList();
        }
        if (team1 == null) {
            return Collections.singletonList(team2);
        }
        if (team2 == null) {
            return Collections.singletonList(team1);
        }
        return Arrays.asList(team1, team2);
    }

    public void setJugadores(List<String> jugadores) {
        if (jugadores != null && jugadores.size() >= 2) {
            this.team1 = jugadores.get(0);
            this.team2 = jugadores.get(1);
        }
    }

    public String getResultado() {
        return score;
    }

    public void setResultado(String resultado) {
        this.score = resultado;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRoundName() {
        return roundName;
    }

    public void setRoundName(String roundName) {
        this.roundName = roundName;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }
}
