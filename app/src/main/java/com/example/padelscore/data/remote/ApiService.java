package com.example.padelscore.data.remote;

import com.example.padelscore.model.TournamentResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("tournaments")
    Call<TournamentResponse> getTournaments();
}
