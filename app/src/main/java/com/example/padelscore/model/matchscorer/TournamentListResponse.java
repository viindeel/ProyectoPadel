package com.example.padelscore.model.matchscorer;

import com.google.gson.annotations.SerializedName;
import java.util.List;

// Respuesta del GET api/tournaments
public class TournamentListResponse {

    @SerializedName("tournaments")
    private List<TournamentItem> tournaments;

    @SerializedName("data")
    private List<TournamentItem> data;

    // Algunas APIs usan "data", otras "tournaments"
    public List<TournamentItem> getTournaments() {
        return tournaments != null ? tournaments : data;
    }

    public void setTournaments(List<TournamentItem> tournaments) {
        this.tournaments = tournaments;
    }

    // Torneo individual
    public static class TournamentItem {

        @SerializedName(value = "id", alternate = { "_id" })
        private String id;

        @SerializedName(value = "name", alternate = { "title" })
        private String name;

        @SerializedName(value = "city", alternate = { "location" })
        private String city;

        @SerializedName("country")
        private String country;

        @SerializedName(value = "startDate", alternate = { "start_date", "start" })
        private String startDate;

        @SerializedName(value = "endDate", alternate = { "end_date", "end" })
        private String endDate;

        @SerializedName("dates")
        private String dates;

        @SerializedName("category")
        private String category;

        @SerializedName("surface")
        private String surface;

        @SerializedName("gender")
        private String gender;

        @SerializedName("sport")
        private String sport;

        @SerializedName("discipline")
        private String discipline;

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getStartDate() { return startDate; }
        public void setStartDate(String startDate) { this.startDate = startDate; }
        public String getEndDate() { return endDate; }
        public void setEndDate(String endDate) { this.endDate = endDate; }
        public String getDates() { return dates; }
        public void setDates(String dates) { this.dates = dates; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public String getSurface() { return surface; }
        public void setSurface(String surface) { this.surface = surface; }
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }
        public String getSport() { return sport; }
        public void setSport(String sport) { this.sport = sport; }
        public String getDiscipline() { return discipline; }
        public void setDiscipline(String discipline) { this.discipline = discipline; }
    }
}