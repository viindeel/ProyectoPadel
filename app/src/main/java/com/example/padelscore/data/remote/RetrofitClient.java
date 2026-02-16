package com.example.padelscore.data.remote;

import com.example.padelscore.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Cliente Singleton de Retrofit para consumir el backend Django.
 */
public class RetrofitClient {

    private static RetrofitClient instance;
    private final ApiService apiService;

    /**
     * Constructor privado para patrón Singleton.
    * Configura OkHttpClient para el backend local.
     */
    private RetrofitClient() {
        // Configurar logging interceptor para debug
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

            apiService = retrofit.create(ApiService.class);
    }

    /**
     * Obtiene la instancia única de RetrofitClient (Singleton).
     * Thread-safe mediante sincronización.
     */
    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    /**
     * Obtiene el servicio de la API.
     */
    public ApiService getApiService() {
        return apiService;
    }
}
