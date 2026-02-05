package com.example.padelscore.ui.favorites;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padelscore.R;
import com.example.padelscore.model.Tournament;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private final List<Tournament> favoriteTournaments;

    public FavoritesAdapter(List<Tournament> favoriteTournaments) {
        this.favoriteTournaments = favoriteTournaments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_tournament_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tournament tournament = favoriteTournaments.get(position);
        holder.tournamentName.setText(tournament.getNombre());
        holder.tournamentDates.setText(tournament.getFecha());
    }

    @Override
    public int getItemCount() {
        return favoriteTournaments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tournamentName;
        public TextView tournamentDates;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tournamentName = itemView.findViewById(R.id.tournament_name);
            tournamentDates = itemView.findViewById(R.id.tournament_dates);
        }
    }
}
