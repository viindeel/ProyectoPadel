package com.example.padelscore.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.padelscore.R;
import com.example.padelscore.data.preferences.FavoritesPreferences;
import com.example.padelscore.model.Tournament;
import com.example.padelscore.ui.tournaments.TournamentViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private TournamentViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar loadingProgress;
    private TextView errorMessage;
    private FavoritesAdapter adapter;
    private List<Tournament> favoriteTournaments = new ArrayList<>();
    private FavoritesPreferences favoritesPreferences;
    private LinearLayout emptyStateContainer;
    private TextView emptyStateText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favoritesPreferences = new FavoritesPreferences(requireContext());

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_favorites);
        recyclerView = view.findViewById(R.id.favorites_recycler_view);
        loadingProgress = view.findViewById(R.id.loading_progress_favorites);
        errorMessage = view.findViewById(R.id.error_message_favorites);
        emptyStateContainer = view.findViewById(R.id.empty_state_container_favorites);
        emptyStateText = view.findViewById(R.id.empty_state_text_favorites);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoritesAdapter(favoriteTournaments);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(TournamentViewModel.class);

        viewModel.getTournaments().observe(getViewLifecycleOwner(), tournaments -> {
            favoriteTournaments.clear();
            for (Tournament tournament : tournaments) {
                // Cargar estado de favorito desde SharedPreferences
                boolean isFav = favoritesPreferences.isFavorite(tournament.getId());
                tournament.setFavorite(isFav);

                if (tournament.isFavorite()) {
                    favoriteTournaments.add(tournament);
                }
            }
            adapter.notifyDataSetChanged();
            updateEmptyState();
        });

        viewModel.isLoading().observe(getViewLifecycleOwner(), isLoading -> {
            loadingProgress.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                errorMessage.setText(error);
                errorMessage.setVisibility(View.VISIBLE);
            } else {
                errorMessage.setVisibility(View.GONE);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.refreshTournaments();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void updateEmptyState() {
        if (favoriteTournaments.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyStateContainer.setVisibility(View.VISIBLE);
            emptyStateText.setText("Aún no tienes favoritos, Añade torneos para verlos aquí");
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyStateContainer.setVisibility(View.GONE);
        }
    }
}
