package com.example.padelscore.ui.matches;

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
import com.example.padelscore.ui.tournaments.TournamentViewModel;

public class MatchesFragment extends Fragment {

    private TournamentViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar loadingProgress;
    private TextView errorMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matches, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TournamentViewModel.class);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_matches);
        recyclerView = view.findViewById(R.id.matches_recycler_view);
        loadingProgress = view.findViewById(R.id.loading_progress_matches);
        errorMessage = view.findViewById(R.id.error_message_matches);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getMatches().observe(getViewLifecycleOwner(), matches -> {
            if (matches != null && !matches.isEmpty()) {
                recyclerView.setAdapter(new MatchAdapter(matches));
            }
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
            long tournamentId = viewModel.getSelected().getValue() != null
                ? viewModel.getSelected().getValue().getId()
                : 0;
            if (tournamentId > 0) {
                viewModel.refreshMatches(tournamentId);
            }
            swipeRefreshLayout.setRefreshing(false);
        });

        // Cargar partidos si no hay ninguno
        if (viewModel.getMatches().getValue() == null || viewModel.getMatches().getValue().isEmpty()) {
            long tournamentId = viewModel.getSelected().getValue() != null
                ? viewModel.getSelected().getValue().getId()
                : 0;
            if (tournamentId > 0) {
                viewModel.loadMatches(tournamentId);
            }
        }
    }
}
