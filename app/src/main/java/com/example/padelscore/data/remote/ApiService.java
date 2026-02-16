package com.example.padelscore.data.remote;

import com.example.padelscore.model.Match;
import com.example.padelscore.model.Tournament;
import com.example.padelscore.model.fip.FipMatch;
import com.example.padelscore.model.fip.FipTournament;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
        @GET("tournaments")
        Call<List<Tournament>> getTournaments(
            @Query("year") int year,
            @Query("after_date") String afterDate,
            @Query("before_date") String beforeDate
        );

    @GET("tournaments/{id}/matches")
    Call<List<Match>> getMatches(@Path("id") String id);

    @GET("matches")
    Call<List<Match>> getMatchesByDate(@Query("date") String date);

    @GET("fip/matches")
    Call<List<Match>> getFipMatchesByDate(@Query("date") String date);

    @GET("fip/tournaments")
    Call<List<FipTournament>> getFipTournaments(@Query("year") int year);

    @GET("fip/tournaments/{slug}/matches")
    Call<List<FipMatch>> getFipMatches(@Path("slug") String slug, @Query("day") int day);
}
