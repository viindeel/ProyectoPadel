package com.example.padelscore.data.remote;

import com.example.padelscore.model.Match;
import com.example.padelscore.model.MatchResponse;
import com.example.padelscore.model.Tournament;
import com.example.padelscore.model.TournamentResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("tournaments")
    Call<TournamentResponse> getTournaments();

    @GET("tournaments/{id}")
    Call<Tournament> getTournamentDetail(@Path("id") long id);

    @GET("tournaments/{id}/matches")
    Call<MatchResponse> getMatches(@Path("id") long id);
}
