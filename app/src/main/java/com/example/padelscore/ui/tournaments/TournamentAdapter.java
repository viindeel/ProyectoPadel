package com.example.padelscore.ui.tournaments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padelscore.R;
import com.example.padelscore.model.Torneo;

import java.util.ArrayList;
import java.util.List;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.ViewHolder> {

    private List<Torneo> torneos;

    public TournamentAdapter() {
        this.torneos = new ArrayList<>();
    }

    public void setTorneos(List<Torneo> torneos) {
        this.torneos = torneos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tournament, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Torneo torneo = torneos.get(position);
        holder.name.setText(torneo.nombre);
        holder.location.setText(torneo.ubicacion);
        String fechaInicio = torneo.fecha_inicio != null ? torneo.fecha_inicio : "";
        String fechaFin = torneo.fecha_fin != null ? torneo.fecha_fin : "";
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
        holder.status.setText("Estado: " + mapStatus(torneo.status));


        holder.itemView.setOnClickListener(v -> {
            // TODO: Implementar navegación a detalle de torneo si es necesario
            // Por ahora, solo mostramos un Toast o dejamos vacío
        });
    }

    @Override
    public int getItemCount() {
        return torneos.size();
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