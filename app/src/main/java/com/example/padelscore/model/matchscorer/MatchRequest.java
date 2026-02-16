package com.example.padelscore.model.matchscorer;

/**
 * POJO para el cuerpo (body) de la petición POST a screen/lst/{tournamentId}.
 *
 * Aunque el endpoint acepta un body, en la práctica puede estar vacío o contener
 * parámetros opcionales según la necesidad.
 *
 * Ejemplo de uso:
 * <code>
 * MatchRequest request = new MatchRequest();
 * // Si necesitas añadir parámetros en el futuro, añádelos aquí
 * </code>
 */
public class MatchRequest {

    // Por ahora el body puede estar vacío.
    // Añade propiedades si la API requiere parámetros específicos en el futuro.

    // Ejemplo de campos que podrías necesitar:
    // private String filter;
    // private String sortBy;
    // private int page;

    public MatchRequest() {
        // Constructor vacío necesario para Gson
    }

    // Getters y setters se añadirán cuando se necesiten campos
}

