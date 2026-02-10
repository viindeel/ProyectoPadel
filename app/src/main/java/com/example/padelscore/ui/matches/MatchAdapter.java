package com.example.padelscore.ui.matches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padelscore.R;
import com.example.padelscore.model.Match;

import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {

    private final List<Match> matches;

    public MatchAdapter(List<Match> matches) {
        this.matches = matches;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Match match = matches.get(position);
        holder.fecha.setText(match.getFecha() != null ? match.getFecha() : "Fecha no disponible");
        holder.estado.setText(match.getEstado() != null ? match.getEstado() : "Sin estado");

        String jugadores = "";
        if (match.getJugadores() != null && !match.getJugadores().isEmpty()) {
            jugadores = match.getJugadores().toString();
        } else {
            jugadores = "Jugadores no disponibles";
        }
        holder.jugadores.setText(jugadores);

        holder.resultado.setText(match.getResultado() != null ? match.getResultado() : "Sin resultado");
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView fecha;
        public final TextView estado;
        public final TextView jugadores;
        public final TextView resultado;

        public ViewHolder(View view) {
            super(view);
            fecha = view.findViewById(R.id.match_fecha);
            estado = view.findViewById(R.id.match_estado);
            jugadores = view.findViewById(R.id.match_jugadores);
            resultado = view.findViewById(R.id.match_resultado);
        }
    }
}

