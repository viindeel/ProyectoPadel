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

/**
 * Interfaz de servicio para la API de MatchScorer.
 *
 * NOTA: Los endpoints tienen prefijos distintos:
 * - Torneos: api/tournaments
 * - Partidos: screen/lst/{tournamentId}
 *
 * Retrofit maneja automáticamente las rutas completas concatenando BASE_URL +
 * ruta del endpoint.
 */
public interface PadelApiService {

        /**
         * Obtiene la lista de torneos.
         *
         * GET https://widget.matchscorerlive.com/api/tournaments
         *
         * NOTA: Probando sin parámetros para ver la estructura real de la API
         */
        @GET("api/tournaments")
        Call<List<TournamentListResponse.TournamentItem>> getTournaments();

        /**
         * Obtiene la lista de torneos filtrada por año y ámbito.
         *
         * GET https://widget.matchscorerlive.com/api/tournaments?year=2026&scope=fip
         */
        @GET("api/tournaments")
        Call<List<TournamentListResponse.TournamentItem>> getTournamentsFiltered(
                        @Query("year") int year,
                        @Query("scope") String scope);

        /**
         * Obtiene el detalle de partidos de un torneo específico mediante POST.
         *
         * POST https://widget.matchscorerlive.com/screen/lst/{tournamentId}?t=tol
         *
         * IMPORTANTE: Este endpoint requiere:
         * - {tournamentId} dinámico en la URL mediante @Path
         * - Parámetro de query "t=tol"
         * - Cuerpo de petición (body) con MatchRequest
         *
         * Ejemplo de uso:
         * <code>
         * MatchRequest request = new MatchRequest();
         * service.getMatchDetails(12345, "tol", request);
         * </code>
         *
         * @param tournamentId ID del torneo (se pasa dinámicamente en la URL)
         * @param type         Tipo de vista (siempre "tol")
         * @param request      Objeto con parámetros del cuerpo (puede estar vacío)
         * @return Detalles de los partidos del torneo
         */
        @POST("screen/lst/{tournamentId}")
        Call<MatchDetailResponse> getMatchDetails(
                        @Path("tournamentId") String tournamentId,
                        @Query("t") String type,
                        @Body MatchRequest request);
}
