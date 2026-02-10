package com.example.padelscore.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class FavoritesPreferences {
    private static final String PREFS_NAME = "padelscore_prefs";
    private static final String FAVORITES_KEY = "favoritos_torneos";

    private final SharedPreferences prefs;

    public FavoritesPreferences(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveFavorite(long tournamentId) {
        Set<String> favorites = new HashSet<>(prefs.getStringSet(FAVORITES_KEY, new HashSet<>()));
        favorites.add(String.valueOf(tournamentId));
        prefs.edit().putStringSet(FAVORITES_KEY, favorites).apply();
    }

    public void removeFavorite(long tournamentId) {
        Set<String> favorites = new HashSet<>(prefs.getStringSet(FAVORITES_KEY, new HashSet<>()));
        favorites.remove(String.valueOf(tournamentId));
        prefs.edit().putStringSet(FAVORITES_KEY, favorites).apply();
    }

    public boolean isFavorite(long tournamentId) {
        Set<String> favorites = prefs.getStringSet(FAVORITES_KEY, new HashSet<>());
        return favorites.contains(String.valueOf(tournamentId));
    }

    public Set<String> getAllFavorites() {
        return new HashSet<>(prefs.getStringSet(FAVORITES_KEY, new HashSet<>()));
    }
}

