package com.example.padelscore.ui.tournaments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padelscore.R;
import com.example.padelscore.model.Tournament;

import java.util.List;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.ViewHolder> {

    private final List<Tournament> tournaments;
    private final TournamentViewModel viewModel;

    public TournamentAdapter(List<Tournament> tournaments, TournamentViewModel viewModel) {
        this.tournaments = tournaments;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tournament, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tournament tournament = tournaments.get(position);
        holder.name.setText(tournament.getNombre());
        holder.location.setText(tournament.getUbicacion());

        holder.itemView.setOnClickListener(v -> {
            viewModel.select(tournament);
            viewModel.loadTournamentDetail(tournament.getId());
            Navigation.findNavController(v).navigate(R.id.action_tournamentListFragment_to_tournamentDetailFragment);
        });
    }

    @Override
    public int getItemCount() {
        return tournaments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView name;
        public final TextView location;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tournament_name);
            location = view.findViewById(R.id.tournament_location);
        }
    }
}
