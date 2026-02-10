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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.padelscore.R;
import com.example.padelscore.data.preferences.FavoritesPreferences;
import com.example.padelscore.model.Tournament;

public class TournamentListFragment extends Fragment {

    private TournamentViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar loadingProgress;
    private TextView errorMessage;
    private FavoritesPreferences favoritesPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tournament_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TournamentViewModel.class);

        favoritesPreferences = new FavoritesPreferences(requireContext());

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_tournaments);
        recyclerView = view.findViewById(R.id.tournaments_recycler_view);
        loadingProgress = view.findViewById(R.id.loading_progress);
        errorMessage = view.findViewById(R.id.error_message);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getTournaments().observe(getViewLifecycleOwner(), tournaments -> {
            // Cargar estado de favoritos desde SharedPreferences
            for (Tournament tournament : tournaments) {
                tournament.setFavorite(favoritesPreferences.isFavorite(tournament.getId()));
            }
            recyclerView.setAdapter(new TournamentAdapter(tournaments, viewModel));
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
}
