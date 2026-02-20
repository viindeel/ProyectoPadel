package com.example.padelscore.network;

import com.example.padelscore.model.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/login/")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("/api/tournaments/")
    Call<java.util.List<Torneo>> getTorneos();

    @GET("/api/matches/")
    Call<java.util.List<Partido>> getPartidos(@Query("tournament") Integer torneoId);

    @GET("/api/me/")
    Call<User> getUserProfile(@Header("Authorization") String authHeader);

    @POST("/api/register/")
    Call<RegisterResponse> register(@Body RegisterRequest request);
}
