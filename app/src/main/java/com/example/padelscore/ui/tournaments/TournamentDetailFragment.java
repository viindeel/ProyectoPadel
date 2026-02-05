package com.example.padelscore.ui.tournaments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.padelscore.R;
import com.example.padelscore.model.Tournament;

public class TournamentDetailFragment extends Fragment {

    private TournamentViewModel viewModel;
    private TextView tournamentName;
    private TextView tournamentDates;
    private TextView tournamentDetails;
    private ImageView favoriteIcon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tournament_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TournamentViewModel.class);

        tournamentName = view.findViewById(R.id.tournament_name);
        tournamentDates = view.findViewById(R.id.tournament_dates);
        tournamentDetails = view.findViewById(R.id.tournament_details);
        favoriteIcon = view.findViewById(R.id.favorite_icon);

        viewModel.getSelected().observe(getViewLifecycleOwner(), tournament -> {
            if (tournament != null) {
                tournamentName.setText(tournament.getNombre());
                tournamentDates.setText("Fecha: " + tournament.getFecha());
                tournamentDetails.setText(tournament.getUbicacion());
                updateFavoriteIcon(tournament);
            }
        });

        favoriteIcon.setOnClickListener(v -> {
            Tournament tournament = viewModel.getSelected().getValue();
            if (tournament != null) {
                tournament.setFavorite(!tournament.isFavorite());
                updateFavoriteIcon(tournament);
                String message = tournament.isFavorite() ? "Añadido a favoritos" : "Eliminado de favoritos";
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateFavoriteIcon(Tournament tournament) {
        if (tournament.isFavorite()) {
            favoriteIcon.setImageResource(R.drawable.ic_star);
        } else {
            favoriteIcon.setImageResource(R.drawable.ic_star_outline);
        }
    }
}
