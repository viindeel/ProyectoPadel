package com.example.padelscore.ui.matches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.padelscore.R;
import com.example.padelscore.model.Partido;
import java.util.ArrayList;
import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchViewHolder> {
    private List<Partido> matches = new ArrayList<>();

    public void setMatches(List<Partido> matches) {
        this.matches = matches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {
        Partido partido = matches.get(position);
        holder.round.setText(partido.round);
        holder.date.setText(partido.date);
        holder.status.setText(partido.status);
        holder.team1.setText(partido.team1);
        holder.team2.setText(partido.team2);
        holder.score.setText(partido.score);
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    static class MatchViewHolder extends RecyclerView.ViewHolder {
        TextView round, date, status, team1, team2, score;
        MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            round = itemView.findViewById(R.id.match_category_round);
            date = itemView.findViewById(R.id.match_fecha);
            status = itemView.findViewById(R.id.match_estado);
            team1 = itemView.findViewById(R.id.match_jugadores); // Aquí puedes parsear los nombres
            team2 = itemView.findViewById(R.id.match_tournament_name); // Si quieres mostrar el torneo
            score = itemView.findViewById(R.id.match_resultado);
        }
    }
}
