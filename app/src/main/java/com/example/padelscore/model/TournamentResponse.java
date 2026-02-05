package com.example.padelscore.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class TournamentResponse {

    @SerializedName("data")
    private List<Tournament> tournaments;

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }
}
