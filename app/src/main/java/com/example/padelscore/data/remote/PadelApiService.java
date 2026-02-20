package com.example.padelscore.data.remote;

import com.example.padelscore.model.matchscorer.MatchRequest;
import com.example.padelscore.model.matchscorer.MatchDetailResponse;
import com.example.padelscore.model.matchscorer.TournamentListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

// API de MatchScorer — dos prefijos distintos: api/tournaments y screen/lst/{id}
public interface PadelApiService {

        // Todos los torneos, sin filtros
        @GET("api/tournaments")
        Call<List<TournamentListResponse.TournamentItem>> getTournaments();

        // Torneos filtrados por año y scope (ej: year=2026, scope=fip)
        @GET("api/tournaments")
        Call<List<TournamentListResponse.TournamentItem>> getTournamentsFiltered(
                @Query("year") int year,
                @Query("scope") String scope);

        // Partidos de un torneo — POST con id en la URL, t=tol y body con MatchRequest
        @POST("screen/lst/{tournamentId}")
        Call<MatchDetailResponse> getMatchDetails(
                @Path("tournamentId") String tournamentId,
                @Query("t") String type,
                @Body MatchRequest request);
}