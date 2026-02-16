package com.example.padelscore.model.matchscorer;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * POJO para la respuesta de POST screen/lst/{tournamentId}.
 *
 * Estructura típica de respuesta para partidos:
 * {
 *   "matches": [
 *     {
 *       "id": 67890,
 *       "round": "Final",
 *       "court": "Pista Central",
 *       "status": "live",
 *       "team1": {...},
 *       "team2": {...},
 *       "score": {...}
 *     }
 *   ]
 * }
 */
public class MatchDetailResponse {

    @SerializedName("matches")
    private List<MatchItem> matches;

    @SerializedName("data")
    private List<MatchItem> data;

    @SerializedName("rounds")
    private List<RoundItem> rounds;

    public List<MatchItem> getMatches() {
        // Intenta obtener de "matches", si no de "data"
        if (matches != null) return matches;
        if (data != null) return data;

        // Si la estructura usa "rounds", extrae los partidos
        if (rounds != null && !rounds.isEmpty()) {
            return rounds.get(0).getMatches();
        }
        return null;
    }

    public void setMatches(List<MatchItem> matches) {
        this.matches = matches;
    }

    public List<RoundItem> getRounds() {
        return rounds;
    }

    public void setRounds(List<RoundItem> rounds) {
        this.rounds = rounds;
    }

    /**
     * Representa una ronda dentro del torneo (usado en algunas estructuras).
     */
    public static class RoundItem {

        @SerializedName("name")
        private String name;

        @SerializedName("matches")
        private List<MatchItem> matches;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<MatchItem> getMatches() {
            return matches;
        }

        public void setMatches(List<MatchItem> matches) {
            this.matches = matches;
        }
    }

    /**
     * Representa un partido individual.
     */
    public static class MatchItem {

        @SerializedName("id")
        private long id;

        @SerializedName("matchId")
        private Long matchId;

        @SerializedName("round")
        private String round;

        @SerializedName("court")
        private String court;

        @SerializedName("status")
        private String status;

        @SerializedName("scheduledTime")
        private String scheduledTime;

        @SerializedName("startTime")
        private String startTime;

        @SerializedName("team1")
        private TeamInfo team1;

        @SerializedName("team2")
        private TeamInfo team2;

        @SerializedName("score")
        private ScoreInfo score;

        // Getters y Setters

        public long getId() {
            return matchId != null ? matchId : id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getRound() {
            return round;
        }

        public void setRound(String round) {
            this.round = round;
        }

        public String getCourt() {
            return court;
        }

        public void setCourt(String court) {
            this.court = court;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getScheduledTime() {
            return scheduledTime;
        }

        public void setScheduledTime(String scheduledTime) {
            this.scheduledTime = scheduledTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public TeamInfo getTeam1() {
            return team1;
        }

        public void setTeam1(TeamInfo team1) {
            this.team1 = team1;
        }

        public TeamInfo getTeam2() {
            return team2;
        }

        public void setTeam2(TeamInfo team2) {
            this.team2 = team2;
        }

        public ScoreInfo getScore() {
            return score;
        }

        public void setScore(ScoreInfo score) {
            this.score = score;
        }
    }

    /**
     * Información de un equipo/pareja.
     */
    public static class TeamInfo {

        @SerializedName("name")
        private String name;

        @SerializedName("player1")
        private PlayerInfo player1;

        @SerializedName("player2")
        private PlayerInfo player2;

        @SerializedName("seed")
        private Integer seed;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public PlayerInfo getPlayer1() {
            return player1;
        }

        public void setPlayer1(PlayerInfo player1) {
            this.player1 = player1;
        }

        public PlayerInfo getPlayer2() {
            return player2;
        }

        public void setPlayer2(PlayerInfo player2) {
            this.player2 = player2;
        }

        public Integer getSeed() {
            return seed;
        }

        public void setSeed(Integer seed) {
            this.seed = seed;
        }
    }

    /**
     * Información de un jugador.
     */
    public static class PlayerInfo {

        @SerializedName("id")
        private long id;

        @SerializedName("name")
        private String name;

        @SerializedName("firstName")
        private String firstName;

        @SerializedName("lastName")
        private String lastName;

        @SerializedName("country")
        private String country;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name != null ? name : (firstName + " " + lastName);
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }

    /**
     * Información del marcador.
     */
    public static class ScoreInfo {

        @SerializedName("team1Sets")
        private int team1Sets;

        @SerializedName("team2Sets")
        private int team2Sets;

        @SerializedName("sets")
        private List<SetScore> sets;

        @SerializedName("currentSet")
        private SetScore currentSet;

        public int getTeam1Sets() {
            return team1Sets;
        }

        public void setTeam1Sets(int team1Sets) {
            this.team1Sets = team1Sets;
        }

        public int getTeam2Sets() {
            return team2Sets;
        }

        public void setTeam2Sets(int team2Sets) {
            this.team2Sets = team2Sets;
        }

        public List<SetScore> getSets() {
            return sets;
        }

        public void setSets(List<SetScore> sets) {
            this.sets = sets;
        }

        public SetScore getCurrentSet() {
            return currentSet;
        }

        public void setCurrentSet(SetScore currentSet) {
            this.currentSet = currentSet;
        }
    }

    /**
     * Puntuación de un set.
     */
    public static class SetScore {

        @SerializedName("team1")
        private int team1;

        @SerializedName("team2")
        private int team2;

        @SerializedName("team1Tiebreak")
        private Integer team1Tiebreak;

        @SerializedName("team2Tiebreak")
        private Integer team2Tiebreak;

        public int getTeam1() {
            return team1;
        }

        public void setTeam1(int team1) {
            this.team1 = team1;
        }

        public int getTeam2() {
            return team2;
        }

        public void setTeam2(int team2) {
            this.team2 = team2;
        }

        public Integer getTeam1Tiebreak() {
            return team1Tiebreak;
        }

        public void setTeam1Tiebreak(Integer team1Tiebreak) {
            this.team1Tiebreak = team1Tiebreak;
        }

        public Integer getTeam2Tiebreak() {
            return team2Tiebreak;
        }

        public void setTeam2Tiebreak(Integer team2Tiebreak) {
            this.team2Tiebreak = team2Tiebreak;
        }
    }
}

