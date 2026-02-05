package com.example.padelscore.data.model;

public class Match {
    private final String id;
    private final String tournamentId;
    private final String player1Id;
    private final String player2Id;
    private final String fecha;
    private final String resultado;

    public Match(String id, String tournamentId, String player1Id, String player2Id, String fecha, String resultado) {
        this.id = id;
        this.tournamentId = tournamentId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.fecha = fecha;
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public String getTournamentId() {
        return tournamentId;
    }

    public String getPlayer1Id() {
        return player1Id;
    }

    public String getPlayer2Id() {
        return player2Id;
    }

    public String getFecha() {
        return fecha;
    }

    public String getResultado() {
        return resultado;
    }
}
