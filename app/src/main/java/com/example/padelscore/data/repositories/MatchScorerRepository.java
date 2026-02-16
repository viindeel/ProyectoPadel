package com.example.padelscore.data.repositories;

import androidx.annotation.NonNull;

import com.example.padelscore.data.remote.ApiService;
import com.example.padelscore.data.remote.RetrofitClient;
import com.example.padelscore.model.Match;
import com.example.padelscore.model.Tournament;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * EJEMPLO de Repository usando el backend Django.
 */
public class MatchScorerRepository {

     private final ApiService apiService;
     private static final int DEFAULT_YEAR = 2026;

    public MatchScorerRepository() {
        // Obtener la instancia única de RetrofitClient
        this.apiService = RetrofitClient.getInstance().getApiService();
    }

    /**
    * Obtiene torneos del año 2026.
     *
     * Ejemplo de uso en ViewModel:
     * <code>
     * repository.getTournaments(new TournamentsCallback() {
     *     &#64;Override
    *     public void onResponse(List<Tournament> tournaments) {
    *         // Actualizar LiveData
     *     }
     *
     *     @Override
     *     public void onFailure(Throwable t) {
     *         // Mostrar error
     *     }
     * });
     * </code>
     */
    public void getTournaments(TournamentsCallback callback) {
        String afterDate = DEFAULT_YEAR + "-01-01";
        String beforeDate = DEFAULT_YEAR + "-12-31";
        apiService.getTournaments(DEFAULT_YEAR, afterDate, beforeDate)
                .enqueue(new Callback<List<Tournament>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Tournament>> call,
                            @NonNull Response<List<Tournament>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Tournament> tournaments = response.body();

                            if (tournaments != null && !tournaments.isEmpty()) {
                                callback.onResponse(tournaments);
                            } else {
                                callback.onFailure(new Exception("No se encontraron torneos"));
                            }
                        } else {
                            callback.onFailure(new Exception("Error en la respuesta: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Tournament>> call,
                            @NonNull Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    /**
     * Obtiene los partidos de un torneo específico.
     *
     * IMPORTANTE: El tournamentId se pasa dinámicamente en la URL.
     *
     * Ejemplo de uso en ViewModel:
     * <code>
     * long tournamentId = tournament.getId();
     * repository.getMatchesForTournament(tournamentId, new MatchesCallback() {
     *     &#64;Override
    *     public void onResponse(List<Match> matches) {
    *         // Actualizar LiveData
     *     }
     *
     *     @Override
     *     public void onFailure(Throwable t) {
     *         // Mostrar error
     *     }
     * });
     * </code>
     */
    public void getMatchesForTournament(String tournamentId, MatchesCallback callback) {
        apiService.getMatches(tournamentId)
                .enqueue(new Callback<List<Match>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Match>> call,
                            @NonNull Response<List<Match>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Match> matches = response.body();

                            if (matches != null) {
                                callback.onResponse(matches);
                            } else {
                                callback.onFailure(new Exception("No se encontraron partidos"));
                            }
                        } else {
                            callback.onFailure(new Exception("Error en la respuesta: " + response.code()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Match>> call, @NonNull Throwable t) {
                        callback.onFailure(t);
                    }
                });
    }

    /**
     * Callback para la lista de torneos.
     */
    public interface TournamentsCallback {
        void onResponse(List<Tournament> tournaments);

        void onFailure(Throwable t);
    }

    /**
     * Callback para la lista de partidos.
     */
    public interface MatchesCallback {
        void onResponse(List<Match> matches);

        void onFailure(Throwable t);
    }

}
