package com.example.padelscore.ui.tournaments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.PopupMenu;
import android.widget.ImageView;
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class TournamentListFragment extends Fragment {

    private TournamentViewModel viewModel;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ProgressBar loadingProgress;
    private TextView errorMessage;
    private FavoritesPreferences favoritesPreferences;
    private ImageView sortButton;
    private LinearLayout emptyStateContainer;
    private TextView emptyStateText;
    private int currentSortOrder = 1; // 0: Por defecto, 1: Fecha, 2: Nombre, 3: Favoritos
    private List<Tournament> currentTournaments = new ArrayList<>();

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
        sortButton = view.findViewById(R.id.sort_button);
        emptyStateContainer = view.findViewById(R.id.empty_state_container);
        emptyStateText = view.findViewById(R.id.empty_state_text);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Click listener para el botón de ordenamiento
        sortButton.setOnClickListener(v -> showSortMenu(v));

        viewModel.getTournaments().observe(getViewLifecycleOwner(), tournaments -> {
            // Cargar estado de favoritos desde SharedPreferences
            for (Tournament tournament : tournaments) {
                tournament.setFavorite(favoritesPreferences.isFavorite(tournament.getId()));
            }
            currentTournaments = new ArrayList<>(tournaments);
            sortTournaments(currentSortOrder, currentTournaments);
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

    private void showSortMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.sort_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.sort_by_date) {
                currentSortOrder = 1;
                sortTournaments(currentSortOrder, currentTournaments);
                return true;
            } else if (item.getItemId() == R.id.sort_by_name) {
                currentSortOrder = 2;
                sortTournaments(currentSortOrder, currentTournaments);
                return true;
            } else if (item.getItemId() == R.id.sort_by_favorites) {
                currentSortOrder = 3;
                sortTournaments(currentSortOrder, currentTournaments);
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    private void sortTournaments(int sortOrder, List<Tournament> input) {
        List<Tournament> sorted = new ArrayList<>(input);

        switch (sortOrder) {
            case 1: // Por fecha (más próximos primero)
                sorted.sort((t1, t2) -> {
                    LocalDate date1 = parseIsoDate(t1.getFecha());
                    LocalDate date2 = parseIsoDate(t2.getFecha());
                    return date1.compareTo(date2);
                });
                break;
            case 2: // Por nombre (A-Z)
                sorted.sort((t1, t2) -> {
                    String nombre1 = t1.getNombre() != null ? t1.getNombre() : "";
                    String nombre2 = t2.getNombre() != null ? t2.getNombre() : "";
                    return nombre1.compareToIgnoreCase(nombre2);
                });
                break;
            case 3: // Favoritos primero
                sorted.sort((t1, t2) -> Boolean.compare(t2.isFavorite(), t1.isFavorite()));
                break;
        }

        recyclerView.setAdapter(new TournamentAdapter(sorted, viewModel));
        updateEmptyState();
    }

    private LocalDate parseIsoDate(String rawDate) {
        if (rawDate == null || rawDate.trim().isEmpty()) {
            return LocalDate.of(2099, 12, 31);
        }

        String value = rawDate.trim();
        List<DateTimeFormatter> dateFormats = Arrays.asList(
                DateTimeFormatter.ISO_LOCAL_DATE,
                DateTimeFormatter.ofPattern("yyyy-M-d", Locale.getDefault()),
                DateTimeFormatter.ofPattern("yyyy/MM/dd", Locale.getDefault())
        );

        for (DateTimeFormatter formatter : dateFormats) {
            try {
                return LocalDate.parse(value, formatter);
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }

        try {
            Instant instant = Instant.parse(value);
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC).toLocalDate();
        } catch (DateTimeParseException ignored) {
            // Try other datetime formats
        }

        List<DateTimeFormatter> dateTimeFormats = Arrays.asList(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.getDefault()),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.getDefault())
        );

        for (DateTimeFormatter formatter : dateTimeFormats) {
            try {
                return LocalDateTime.parse(value, formatter).toLocalDate();
            } catch (DateTimeParseException ignored) {
                // Try next format
            }
        }

        return LocalDate.of(2099, 12, 31);
    }

    private void updateEmptyState() {
        if (currentTournaments.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyStateContainer.setVisibility(View.VISIBLE);
            emptyStateText.setText("⚽ No hay torneos aún\n\nDesliza hacia abajo para refrescar");
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            emptyStateContainer.setVisibility(View.GONE);
        }
    }
}
