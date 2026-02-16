package com.example.padelscore.ui.matches;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.example.padelscore.model.Match;
import com.example.padelscore.ui.tournaments.TournamentViewModel;

import java.util.List;

public class MatchesFragment extends Fragment {

    private TournamentViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar loadingProgress;
    private TextView errorMessage;
    private LinearLayout emptyStateContainer;
    private TextView emptyStateText;
    private FavoritesPreferences favoritesPreferences;
    private Handler autoRefreshHandler;
    private Runnable autoRefreshRunnable;
    private static final long REFRESH_INTERVAL = 30000; // 30 segundos
    private static final String TODAY_LABEL = "Hoy";
    private static final String OTHER_LABEL = "Otros dias";
    private static final String FAVORITES_LABEL = "Favoritos hoy";
    private static final String NON_FAVORITES_LABEL = "Otros hoy";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matches, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TournamentViewModel.class);
        favoritesPreferences = new FavoritesPreferences(requireContext());

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_matches);
        recyclerView = view.findViewById(R.id.matches_recycler_view);
        loadingProgress = view.findViewById(R.id.loading_progress_matches);
        errorMessage = view.findViewById(R.id.error_message_matches);
        emptyStateContainer = view.findViewById(R.id.empty_state_container_matches);
        emptyStateText = view.findViewById(R.id.empty_state_text_matches);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        autoRefreshHandler = new Handler(Looper.getMainLooper());

        viewModel.getMatches().observe(getViewLifecycleOwner(), matches -> {
            updateMatchList(matches);
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
            String tournamentId = viewModel.getSelected().getValue() != null
                ? viewModel.getSelected().getValue().getId()
                : null;
            if (tournamentId != null) {
                viewModel.refreshMatches(tournamentId);
            } else {
                loadTodayMatchesForFavorites();
            }
            swipeRefreshLayout.setRefreshing(false);
        });

        // Cargar partidos si no hay ninguno
        if (viewModel.getMatches().getValue() == null || viewModel.getMatches().getValue().isEmpty()) {
            String tournamentId = viewModel.getSelected().getValue() != null
                ? viewModel.getSelected().getValue().getId()
                : null;
            if (tournamentId != null) {
                viewModel.loadMatches(tournamentId);
                startAutoRefresh(tournamentId);
            } else {
                loadTodayMatchesForFavorites();
                startAutoRefresh(null);
            }
        }

        updateEmptyState();
    }

    private void updateMatchList(List<com.example.padelscore.model.Match> matches) {
        if (matches == null || matches.isEmpty()) {
            updateEmptyState();
            return;
        }

        List<Match> todayMatches = new java.util.ArrayList<>();
        List<com.example.padelscore.model.Match> otherMatches = new java.util.ArrayList<>();
        for (com.example.padelscore.model.Match match : matches) {
            if (isToday(match)) {
                todayMatches.add(match);
            } else {
                otherMatches.add(match);
            }
        }

        boolean hasAny = !todayMatches.isEmpty() || !otherMatches.isEmpty();
        if (!hasAny) {
            updateEmptyState();
            return;
        }

        java.util.Set<String> favorites = favoritesPreferences.getAllFavorites();
        if (favorites != null && !favorites.isEmpty() && !todayMatches.isEmpty()) {
            List<Match> favoriteToday = new java.util.ArrayList<>();
            List<Match> otherToday = new java.util.ArrayList<>();
            for (Match match : todayMatches) {
                String tournamentId = match.getTournamentId();
                if (tournamentId != null && favorites.contains(tournamentId)) {
                    favoriteToday.add(match);
                } else {
                    otherToday.add(match);
                }
            }
            recyclerView.setAdapter(new MatchSectionAdapter(favoriteToday, otherToday, FAVORITES_LABEL, NON_FAVORITES_LABEL));
        } else {
            recyclerView.setAdapter(new MatchSectionAdapter(todayMatches, otherMatches, TODAY_LABEL, OTHER_LABEL));
        }
        recyclerView.setVisibility(View.VISIBLE);
        emptyStateContainer.setVisibility(View.GONE);
    }

    private boolean isToday(com.example.padelscore.model.Match match) {
        String rawTime = match.getStartTime() != null ? match.getStartTime() : match.getFecha();
        if (rawTime == null || rawTime.trim().isEmpty()) {
            return false;
        }

        java.util.Date parsed = parseDate(rawTime.trim());
        if (parsed == null) {
            return false;
        }

        java.util.Calendar matchCal = java.util.Calendar.getInstance();
        matchCal.setTime(parsed);
        java.util.Calendar todayCal = java.util.Calendar.getInstance();

        return matchCal.get(java.util.Calendar.YEAR) == todayCal.get(java.util.Calendar.YEAR)
                && matchCal.get(java.util.Calendar.DAY_OF_YEAR) == todayCal.get(java.util.Calendar.DAY_OF_YEAR);
    }

    private java.util.Date parseDate(String value) {
        String[] patterns = new String[] {
                "yyyy-MM-dd'T'HH:mm:ss'Z'",
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd HH:mm",
                "yyyy-MM-dd"
        };

        for (String pattern : patterns) {
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(pattern, java.util.Locale.getDefault());
                if (pattern.endsWith("'Z'")) {
                    sdf.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));
                }
                return sdf.parse(value);
            } catch (java.text.ParseException ignored) {
                // Try next format
            }
        }
        return null;
    }

    private void startAutoRefresh(String tournamentId) {
        autoRefreshRunnable = () -> {
            if (!isAdded()) {
                return;
            }
            if (tournamentId != null && !tournamentId.isEmpty()) {
                viewModel.loadMatches(tournamentId);
            } else {
                loadTodayMatchesForFavorites();
            }
                // Programar el siguiente refresh
            autoRefreshHandler.postDelayed(autoRefreshRunnable, REFRESH_INTERVAL);
        };
        autoRefreshHandler.postDelayed(autoRefreshRunnable, REFRESH_INTERVAL);
    }

    private void stopAutoRefresh() {
        if (autoRefreshHandler != null && autoRefreshRunnable != null) {
            autoRefreshHandler.removeCallbacks(autoRefreshRunnable);
        }
    }

    private void updateEmptyState() {
        if (viewModel.getMatches().getValue() == null || viewModel.getMatches().getValue().isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyStateContainer.setVisibility(View.VISIBLE);

            if (viewModel.getSelected().getValue() == null) {
                emptyStateText.setText("⚽ No hay partidos hoy\n\nSe actualizan cada 30 segundos");
            } else {
                emptyStateText.setText("⚽ No hay partidos hoy\n\nLos partidos se actualizan cada 30 segundos");
            }
        }
    }

    private void loadTodayMatchesForFavorites() {
        java.util.Set<String> favorites = favoritesPreferences.getAllFavorites();
        if (favorites != null && !favorites.isEmpty()) {
            viewModel.loadMatchesByDate(getTodayQueryDate(), favorites);
        } else {
            viewModel.loadMatchesByDate(getTodayQueryDate());
        }
    }

    private String getTodayQueryDate() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        return sdf.format(new java.util.Date());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopAutoRefresh();
    }
}
