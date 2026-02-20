package com.example.padelscore.util;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class TokenManager {
    private static final String PREF_NAME = "secure_prefs";
    private static final String KEY_TOKEN = "jwt_token";

    public static void saveToken(Context context, String token) {
        try {
            SharedPreferences prefs = getPrefs(context);
            prefs.edit().putString(KEY_TOKEN, token).apply();
        } catch (Exception ignored) {}
    }

    public static String getToken(Context context) {
        try {
            SharedPreferences prefs = getPrefs(context);
            return prefs.getString(KEY_TOKEN, null);
        } catch (Exception e) { return null; }
    }

    private static SharedPreferences getPrefs(Context context) throws GeneralSecurityException, IOException {
        String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        return EncryptedSharedPreferences.create(
                PREF_NAME,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }
}

