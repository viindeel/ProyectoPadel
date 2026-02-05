package com.example.padelscore.model;

import com.example.padelscore.data.model.Player;

import java.util.List;

public class Match {
    private long id;
    private List<Player> players;
    private String result;

    public Match(long id, List<Player> players, String result) {
        this.id = id;
        this.players = players;
        this.result = result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
