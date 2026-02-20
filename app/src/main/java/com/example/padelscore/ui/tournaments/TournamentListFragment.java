package com.example.padelscore.ui.tournaments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.padelscore.R;
import com.example.padelscore.model.Torneo;
import com.example.padelscore.network.ApiClient;
import com.example.padelscore.network.ApiService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TournamentListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar loadingProgress;
    private TextView errorMessage;
    private TournamentAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tournament_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.tournaments_recycler_view);
        loadingProgress = view.findViewById(R.id.loading_progress);
        errorMessage = view.findViewById(R.id.error_message);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TournamentAdapter();
        recyclerView.setAdapter(adapter);
        loadTorneos();
    }

    private void loadTorneos() {
        loadingProgress.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.GONE);
        ApiService apiService = ApiClient.getClient(requireContext()).create(ApiService.class);
        apiService.getTorneos().enqueue(new Callback<List<Torneo>>() {
            @Override
            public void onResponse(Call<List<Torneo>> call, Response<List<Torneo>> response) {
                loadingProgress.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Torneo> torneos = response.body();
                    if (torneos.isEmpty()) {
                        errorMessage.setText("No hay torneos disponibles para mostrar.\n\n" +
                                "Verifica que tu backend tiene datos para el año actual (" + java.time.LocalDate.now().getYear() + ")\n" +
                                "o revisa la respuesta de la API.");
                        errorMessage.setVisibility(View.VISIBLE);
                        adapter.setTorneos(new java.util.ArrayList<>());
                    } else {
                        errorMessage.setVisibility(View.GONE);
                        adapter.setTorneos(torneos);
                    }
                } else {
                    String error = "Error al cargar torneos. Código: " + response.code();
                    if (response.errorBody() != null) {
                        try { error += "\n" + response.errorBody().string(); } catch (Exception ignored) {}
                    }
                    errorMessage.setText(error);
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<Torneo>> call, Throwable t) {
                loadingProgress.setVisibility(View.GONE);
                errorMessage.setText("Error de red: " + t.getMessage());
                errorMessage.setVisibility(View.VISIBLE);
            }
        });
    }
}
