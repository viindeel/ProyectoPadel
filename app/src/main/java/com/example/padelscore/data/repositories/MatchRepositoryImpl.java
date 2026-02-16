package com.example.padelscore.data.repositories;

import android.util.Log;
import androidx.annotation.NonNull;

import com.example.padelscore.data.remote.ApiService;
import com.example.padelscore.data.remote.RetrofitClient;
import com.example.padelscore.model.Match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchRepositoryImpl implements MatchRepository {

    private final ApiService apiService;
    private static final String TAG = "MatchRepository";

    public MatchRepositoryImpl() {
        // Usar el Singleton de RetrofitClient con el backend Django
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    @Override
    public void getMatches(String tournamentId, MatchesCallback callback) {
        Log.d(TAG, "Solicitando partidos para torneo: " + tournamentId);

        apiService.getMatches(tournamentId)
                .enqueue(new Callback<List<Match>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Match>> call,
                                           @NonNull Response<List<Match>> response) {
                        Log.d(TAG, "Respuesta API recibida. Código: " + response.code());
                        Log.d(TAG, "URL completa: " + call.request().url());

                        try {
                            if (response.isSuccessful()) {
                                List<Match> matches = response.body();
                                if (matches != null) {
                                    Log.d(TAG, "Partidos obtenidos: " + matches.size());
                                    callback.onResponse(matches);
                                } else {
                                    Log.e(TAG, "Body es null");
                                    callback.onResponse(new ArrayList<>());
                                }
                            } else {
                                String errorBody = response.errorBody() != null
                                        ? response.errorBody().string()
                                        : "sin cuerpo";
                                Log.e(TAG, "Error en la respuesta: " + response.code() + " - " + errorBody);
                                callback.onFailure(new IOException("Error en la API: " + response.code()));
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error procesando respuesta", e);
                            callback.onFailure(new IOException("Error procesando la respuesta"));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Match>> call, @NonNull Throwable t) {
                        Log.e(TAG, "Error en API de tipo: " + t.getClass().getName());
                        Log.e(TAG, "Mensaje de error: " + t.getMessage(), t);
                        callback.onFailure(t);
                    }
                });
    }

    @Override
    public void getMatchesByDate(String date, MatchesCallback callback) {
        Log.d(TAG, "Solicitando partidos por fecha: " + date);

        apiService.getMatchesByDate(date)
                .enqueue(new Callback<List<Match>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Match>> call,
                                           @NonNull Response<List<Match>> response) {
                        Log.d(TAG, "Respuesta API recibida. Código: " + response.code());
                        Log.d(TAG, "URL completa: " + call.request().url());

                        try {
                            if (response.isSuccessful()) {
                                List<Match> matches = response.body();
                                if (matches != null) {
                                    Log.d(TAG, "Partidos obtenidos: " + matches.size());
                                    callback.onResponse(matches);
                                } else {
                                    Log.e(TAG, "Body es null");
                                    callback.onResponse(new ArrayList<>());
                                }
                            } else {
                                String errorBody = response.errorBody() != null
                                        ? response.errorBody().string()
                                        : "sin cuerpo";
                                Log.e(TAG, "Error en la respuesta: " + response.code() + " - " + errorBody);
                                callback.onFailure(new IOException("Error en la API: " + response.code()));
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error procesando respuesta", e);
                            callback.onFailure(new IOException("Error procesando la respuesta"));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Match>> call, @NonNull Throwable t) {
                        Log.e(TAG, "Error en API de tipo: " + t.getClass().getName());
                        Log.e(TAG, "Mensaje de error: " + t.getMessage(), t);
                        callback.onFailure(t);
                    }
                });
    }

    @Override
    public void getFipMatchesByDate(String date, MatchesCallback callback) {
        Log.d(TAG, "Solicitando partidos FIP por fecha: " + date);

        apiService.getFipMatchesByDate(date)
                .enqueue(new Callback<List<Match>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Match>> call,
                                           @NonNull Response<List<Match>> response) {
                        Log.d(TAG, "Respuesta API recibida. Código: " + response.code());
                        Log.d(TAG, "URL completa: " + call.request().url());

                        try {
                            if (response.isSuccessful()) {
                                List<Match> matches = response.body();
                                if (matches != null) {
                                    Log.d(TAG, "Partidos obtenidos: " + matches.size());
                                    callback.onResponse(matches);
                                } else {
                                    Log.e(TAG, "Body es null");
                                    callback.onResponse(new ArrayList<>());
                                }
                            } else {
                                String errorBody = response.errorBody() != null
                                        ? response.errorBody().string()
                                        : "sin cuerpo";
                                Log.e(TAG, "Error en la respuesta: " + response.code() + " - " + errorBody);
                                callback.onFailure(new IOException("Error en la API: " + response.code()));
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error procesando respuesta", e);
                            callback.onFailure(new IOException("Error procesando la respuesta"));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Match>> call, @NonNull Throwable t) {
                        Log.e(TAG, "Error en API de tipo: " + t.getClass().getName());
                        Log.e(TAG, "Mensaje de error: " + t.getMessage(), t);
                        callback.onFailure(t);
                    }
                });
    }
}

