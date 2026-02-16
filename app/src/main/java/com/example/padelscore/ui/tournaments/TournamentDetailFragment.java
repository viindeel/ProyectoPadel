package com.example.padelscore.ui.tournaments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.padelscore.R;
import com.example.padelscore.data.preferences.FavoritesPreferences;
import com.example.padelscore.model.Tournament;

public class TournamentDetailFragment extends Fragment {

    private TournamentViewModel viewModel;
    private TextView tournamentName;
    private TextView tournamentLocation;
    private TextView tournamentDates;
    private TextView tournamentDetails;
    private ImageView favoriteIcon;
    private ProgressBar loadingProgress;
    private TextView errorMessage;
    private FavoritesPreferences favoritesPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tournament_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TournamentViewModel.class);

        favoritesPreferences = new FavoritesPreferences(requireContext());

        tournamentName = view.findViewById(R.id.tournament_name);
        tournamentLocation = view.findViewById(R.id.tournament_location);
        tournamentDates = view.findViewById(R.id.tournament_dates);
        tournamentDetails = view.findViewById(R.id.tournament_details);
        favoriteIcon = view.findViewById(R.id.favorite_icon);
        loadingProgress = view.findViewById(R.id.loading_progress_detail);
        errorMessage = view.findViewById(R.id.error_message_detail);

        viewModel.getSelected().observe(getViewLifecycleOwner(), tournament -> {
            if (tournament != null) {
                // Cargar estado de favorito desde SharedPreferences
                boolean isFav = favoritesPreferences.isFavorite(tournament.getId());
                tournament.setFavorite(isFav);

                displayTournamentDetail(tournament);
                updateFavoriteIcon(tournament);
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

        favoriteIcon.setOnClickListener(v -> {
            Tournament tournament = viewModel.getSelected().getValue();
            if (tournament != null) {
                boolean newState = !tournament.isFavorite();
                tournament.setFavorite(newState);

                // Guardar en SharedPreferences
                if (newState) {
                    favoritesPreferences.saveFavorite(tournament.getId());
                } else {
                    favoritesPreferences.removeFavorite(tournament.getId());
                }

                updateFavoriteIcon(tournament);
                String message = newState ? "Añadido a favoritos" : "Eliminado de favoritos";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayTournamentDetail(Tournament tournament) {
        tournamentName.setText(tournament.getNombre() != null ? tournament.getNombre() : "");
        String location = tournament.getUbicacion() != null ? tournament.getUbicacion() : "";
        String country = tournament.getCountry() != null ? tournament.getCountry() : "";
        if (!location.isEmpty() && !country.isEmpty()) {
            tournamentLocation.setText(location + ", " + country);
        } else if (!location.isEmpty()) {
            tournamentLocation.setText(location);
        } else if (!country.isEmpty()) {
            tournamentLocation.setText(country);
        } else {
            tournamentLocation.setText("Ubicacion por confirmar");
        }
        String dateRange = "Fecha: " + (tournament.getFecha() != null ? tournament.getFecha() : "N/A");
        if (tournament.getFechaFin() != null && !tournament.getFechaFin().isEmpty()) {
            dateRange += " a " + tournament.getFechaFin();
        }
        tournamentDates.setText(dateRange);

        StringBuilder details = new StringBuilder();
        if (tournament.getCountry() != null && !tournament.getCountry().isEmpty()) {
            details.append("Pais: ").append(tournament.getCountry());
        }
        if (tournament.getStatus() != null && !tournament.getStatus().isEmpty()) {
            if (details.length() > 0) details.append("\n");
            details.append("Estado: ").append(tournament.getStatus());
        }
        if (details.length() == 0) {
            details.append("Sin detalles disponibles");
        }
        tournamentDetails.setText(details.toString());
    }


    private void updateFavoriteIcon(Tournament tournament) {
        if (tournament.isFavorite()) {
            favoriteIcon.setImageResource(R.drawable.ic_star);
        } else {
            favoriteIcon.setImageResource(R.drawable.ic_star_outline);
        }

        // Animar el icono con bounce scale
        favoriteIcon.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce_scale));
    }
}
