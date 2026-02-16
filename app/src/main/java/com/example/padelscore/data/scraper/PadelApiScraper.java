package com.example.padelscore.data.scraper;

import android.util.Log;
import com.example.padelscore.model.Match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * NOTA: Este scraper está temporalmente deshabilitado.
 * Se debe usar RetrofitClient y PadelApiService en su lugar.
 * Ver NETWORK_SETUP.md para más información.
 */
public class PadelApiScraper {

    private static final String TAG = "PadelApiScraper";

    /**
     * Método temporal que retorna lista vacía.
     * TODO: Migrar a usar RetrofitClient.getInstance().getApiService()
     */
    public static List<Match> scrapeMatches(long tournamentId) throws IOException {
        Log.d(TAG, "ADVERTENCIA: scrapeMatches está deprecated. Usar PadelApiService en su lugar.");
        return new ArrayList<>();
    }
}

