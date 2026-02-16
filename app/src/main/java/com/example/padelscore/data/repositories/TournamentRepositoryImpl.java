package com.example.padelscore.data.repositories;

import android.util.Log;
import androidx.annotation.NonNull;

import com.example.padelscore.data.remote.ApiService;
import com.example.padelscore.data.remote.RetrofitClient;
import com.example.padelscore.model.Tournament;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TournamentRepositoryImpl implements TournamentRepository {

    private final ApiService apiService;
    private static final String TAG = "TournamentRepository";
    private static final int DEFAULT_YEAR = 2026;
    private List<Tournament> cachedTournaments = new ArrayList<>();

    public TournamentRepositoryImpl() {
        // Usar el Singleton de RetrofitClient con el backend Django
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    @Override
    public void getTournaments(TournamentsCallback callback) {
        Log.d(TAG, "Solicitando torneos de la API");

        String afterDate = DEFAULT_YEAR + "-01-01";
        String beforeDate = DEFAULT_YEAR + "-12-31";

        apiService.getTournaments(DEFAULT_YEAR, afterDate, beforeDate)
                .enqueue(new Callback<List<Tournament>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Tournament>> call,
                            @NonNull Response<List<Tournament>> response) {
                        Log.d(TAG, "Respuesta recibida. Código: " + response.code());
                        Log.d(TAG, "URL: " + call.request().url());

                        try {
                            if (response.isSuccessful()) {
                                List<Tournament> tournaments = response.body();
                                if (tournaments != null && !tournaments.isEmpty()) {
                                    cachedTournaments = new ArrayList<>(tournaments);
                                    callback.onResponse(tournaments);
                                } else {
                                    Log.w(TAG, "Lista de torneos vacía");
                                    callback.onFailure(new IOException("La API no devolvió torneos"));
                                }
                            } else {
                                String errorBody = response.errorBody() != null
                                        ? response.errorBody().string()
                                        : "sin cuerpo";
                                Log.e(TAG, "Error HTTP " + response.code() + ": " + errorBody);
                                callback.onFailure(new IOException("Error HTTP: " + response.code()));
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error procesando respuesta", e);
                            callback.onFailure(new IOException("Error procesando la respuesta"));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Tournament>> call,
                            @NonNull Throwable t) {
                        Log.e(TAG, "❌ Error en la petición: " + t.getClass().getSimpleName());
                        Log.e(TAG, "Mensaje: " + t.getMessage(), t);

                        // Si el error es de JSON vacío, mostrar mensaje específico
                        if (t.getMessage() != null && t.getMessage().contains("End of input")) {
                            callback.onFailure(new IOException("La API devolvió una respuesta vacía"));
                        } else {
                            callback.onFailure(t);
                        }
                    }
                });
    }

    @Override
    public void getTournamentDetail(String id, TournamentDetailCallback callback) {
        if (cachedTournaments != null && !cachedTournaments.isEmpty()) {
            for (Tournament tournament : cachedTournaments) {
                if (id.equals(tournament.getId())) {
                    callback.onResponse(tournament);
                    return;
                }
            }
        }

        getTournaments(new TournamentsCallback() {
            @Override
            public void onResponse(List<Tournament> tournaments) {
                for (Tournament tournament : tournaments) {
                    if (id.equals(tournament.getId())) {
                        callback.onResponse(tournament);
                        return;
                    }
                }
                callback.onFailure(new IOException("Torneo no encontrado"));
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        });
    }
}
