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
        String fechaInicio = tournament.getFecha() != null ? tournament.getFecha() : "";
        String fechaFin = tournament.getFechaFin() != null ? tournament.getFechaFin() : "";
        String fecha;
        if (!fechaInicio.isEmpty() && !fechaFin.isEmpty()) {
            fecha = "Del " + fechaInicio + " al " + fechaFin;
        } else if (!fechaInicio.isEmpty()) {
            fecha = fechaInicio;
        } else if (!fechaFin.isEmpty()) {
            fecha = "Hasta " + fechaFin;
        } else {
            fecha = "Fecha por confirmar";
        }
        holder.date.setText(fecha);
        holder.status.setText("Estado: " + mapStatus(tournament.getStatus()));


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
        public final TextView status;
        public final TextView date;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tournament_name);
            location = view.findViewById(R.id.tournament_location);
            status = view.findViewById(R.id.tournament_status);
            date = view.findViewById(R.id.tournament_date);
        }
    }

    private String mapStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return "Sin estado";
        }
        String normalized = status.trim().toLowerCase();
        switch (normalized) {
            case "live":
            case "playing":
            case "en vivo":
            case "en_directo":
            case "en directo":
                return "En directo";
            case "finished":
            case "completed":
            case "finalizado":
                return "Finalizado";
            case "scheduled":
            case "upcoming":
            case "programado":
                return "Programado";
            case "postponed":
            case "pospuesto":
                return "Pospuesto";
            case "cancelled":
            case "canceled":
            case "cancelado":
                return "Cancelado";
            default:
                return status;
        }
    }

}