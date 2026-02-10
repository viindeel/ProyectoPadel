package com.example.padelscore.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MatchResponse {

    @SerializedName("data")
    private List<Match> matches;

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}

