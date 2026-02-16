package com.example.padelscore.model.fip;

import com.google.gson.annotations.SerializedName;

public class FipMatch {

    @SerializedName("match_code")
    private String matchCode;

    @SerializedName("category")
    private String category;

    @SerializedName("round_name")
    private String roundName;

    @SerializedName("scheduled_time")
    private String scheduledTime;

    @SerializedName("team1")
    private String team1;

    @SerializedName("team2")
    private String team2;

    @SerializedName("score")
    private String score;

    @SerializedName("status")
    private String status;

    @SerializedName("day")
    private int day;

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
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

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
