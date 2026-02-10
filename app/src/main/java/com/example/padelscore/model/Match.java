package com.example.padelscore.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Match {
    @SerializedName("id")
    private long id;

    @SerializedName("tournament_id")
    private long tournamentId;

    @SerializedName("date")
    private String fecha;

    @SerializedName("status")
    private String estado;

    @SerializedName("players")
    private List<String> jugadores;

    @SerializedName("result")
    private String resultado;

    public Match(long id, List<String> players, String result) {
        this.id = id;
        this.jugadores = players;
        this.resultado = result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<String> jugadores) {
        this.jugadores = jugadores;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
