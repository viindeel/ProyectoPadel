package com.example.padelscore.data.repositories;

import androidx.annotation.NonNull;

import com.example.padelscore.data.remote.ApiService;
import com.example.padelscore.model.Match;
import com.example.padelscore.model.MatchResponse;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MatchRepositoryImpl implements MatchRepository {

    private final ApiService apiService;

    public MatchRepositoryImpl() {
        final String apiKey = "0Gj1PjSTyVn5MDA3YnB6HeL4ELyhAw1V69Gmc0FH82be970c";

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public okhttp3.Response intercept(@NonNull Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("Authorization", "Bearer " + apiKey)
                                .method(original.method(), original.body())
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://padelapi.org/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.apiService = retrofit.create(ApiService.class);
    }

    @Override
    public void getMatches(long tournamentId, MatchesCallback callback) {
        apiService.getMatches(tournamentId).enqueue(new Callback<MatchResponse>() {
            @Override
            public void onResponse(@NonNull Call<MatchResponse> call, @NonNull Response<MatchResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onResponse(response.body().getMatches());
                } else {
                    callback.onFailure(new IOException("Error al obtener partidos: " + response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<MatchResponse> call, @NonNull Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}

