package com.example.padelscore.ui.matches;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padelscore.R;
import com.example.padelscore.model.Match;

import java.util.ArrayList;
import java.util.List;

public class MatchSectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_MATCH = 1;

    private final List<Item> items = new ArrayList<>();

    public MatchSectionAdapter(List<Match> todayMatches,
                               List<Match> otherMatches,
                               String todayLabel,
                               String otherLabel) {
        if (todayMatches != null && !todayMatches.isEmpty()) {
            items.add(new Item(todayLabel));
            for (Match match : todayMatches) {
                items.add(new Item(match));
            }
        }
        if (otherMatches != null && !otherMatches.isEmpty()) {
            items.add(new Item(otherLabel));
            for (Match match : otherMatches) {
                items.add(new Item(match));
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).isHeader ? TYPE_HEADER : TYPE_MATCH;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.item_match_section, parent, false);
            return new HeaderViewHolder(view);
        }
        View view = inflater.inflate(R.layout.item_match, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).title.setText(item.headerText);
        } else if (holder instanceof MatchViewHolder) {
            bindMatch((MatchViewHolder) holder, item.match);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void bindMatch(MatchViewHolder holder, Match match) {
        holder.fecha.setText(formatTime(match));
        holder.categoryRound.setText(formatCategoryRound(match));
        String rawStatus = match.getEstado() != null ? match.getEstado() : "unknown";
        String rawScore = match.getResultado();
        holder.estado.setText(mapStatus(rawStatus, rawScore));
        applyStatusStyle(holder.estado, rawStatus, rawScore);

        String equipo1 = match.getTeam1() != null ? match.getTeam1() : "Equipo 1";
        String equipo2 = match.getTeam2() != null ? match.getTeam2() : "Equipo 2";
        holder.jugadores.setText(equipo1 + " vs " + equipo2);

        holder.resultado.setText(match.getResultado() != null ? match.getResultado() : "Sin resultado");

        bindTournamentName(holder.tournamentName, match.getTournamentName());
        bindCourt(holder.court, match.getCourt());
    }

    private String mapStatus(String status, String score) {
        String normalized = status.trim().toLowerCase();
        if (shouldTreatAsUpcoming(normalized, score)) {
            return "Programado";
        }
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

    private void applyStatusStyle(TextView view, String status, String score) {
        String normalized = status.trim().toLowerCase();
        int colorRes;
        if (shouldTreatAsUpcoming(normalized, score)) {
            colorRes = R.color.status_scheduled;
        } else {
        switch (normalized) {
            case "live":
            case "playing":
            case "en vivo":
            case "en_directo":
            case "en directo":
                colorRes = R.color.status_live;
                break;
            case "finished":
            case "completed":
            case "finalizado":
                colorRes = R.color.status_finished;
                break;
            case "postponed":
            case "pospuesto":
                colorRes = R.color.status_postponed;
                break;
            case "cancelled":
            case "canceled":
            case "cancelado":
                colorRes = R.color.status_cancelled;
                break;
            default:
                colorRes = R.color.status_scheduled;
                break;
        }
        }

        ViewCompat.setBackgroundTintList(
                view,
                ContextCompat.getColorStateList(view.getContext(), colorRes)
        );
        view.setTextColor(ContextCompat.getColor(view.getContext(), R.color.color_sobre_primario));
    }

    private boolean shouldTreatAsUpcoming(String normalizedStatus, String score) {
        if (normalizedStatus.equals("live") || normalizedStatus.equals("playing")
                || normalizedStatus.equals("en vivo") || normalizedStatus.equals("en_directo")
                || normalizedStatus.equals("en directo")) {
            return false;
        }
        if (normalizedStatus.equals("finished") || normalizedStatus.equals("completed")
                || normalizedStatus.equals("finalizado")) {
            return score == null || score.trim().isEmpty() || score.equals("-");
        }
        return false;
    }

    private static class Item {
        private final boolean isHeader;
        private final String headerText;
        private final Match match;

        private Item(String headerText) {
            this.isHeader = true;
            this.headerText = headerText;
            this.match = null;
        }

        private Item(Match match) {
            this.isHeader = false;
            this.headerText = null;
            this.match = match;
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;

        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.match_section_title);
        }
    }

    static class MatchViewHolder extends RecyclerView.ViewHolder {
        private final TextView fecha;
        private final TextView court;
        private final TextView categoryRound;
        private final TextView estado;
        private final TextView tournamentName;
        private final TextView jugadores;
        private final TextView resultado;

        MatchViewHolder(@NonNull View itemView) {
            super(itemView);
            fecha = itemView.findViewById(R.id.match_fecha);
            court = itemView.findViewById(R.id.match_court);
            categoryRound = itemView.findViewById(R.id.match_category_round);
            estado = itemView.findViewById(R.id.match_estado);
            tournamentName = itemView.findViewById(R.id.match_tournament_name);
            jugadores = itemView.findViewById(R.id.match_jugadores);
            resultado = itemView.findViewById(R.id.match_resultado);
        }
    }

    private void bindTournamentName(TextView view, String name) {
        if (name == null || name.trim().isEmpty()) {
            view.setVisibility(View.GONE);
            return;
        }
        view.setText(name.trim());
        view.setVisibility(View.VISIBLE);
    }

    private void bindCourt(TextView view, String court) {
        if (court == null || court.trim().isEmpty()) {
            view.setVisibility(View.GONE);
            return;
        }
        view.setText(court.trim());
        view.setVisibility(View.VISIBLE);
    }

    private String formatTime(Match match) {
        String scheduled = match.getScheduledTime();
        if (scheduled != null && !scheduled.trim().isEmpty()) {
            return scheduled.trim();
        }
        String start = match.getStartTime();
        if (start != null && !start.trim().isEmpty()) {
            return start.trim();
        }
        return "Hora no disponible";
    }

    private String formatCategoryRound(Match match) {
        String category = match.getCategory() != null ? match.getCategory().trim() : "";
        String round = match.getRoundName() != null ? match.getRoundName().trim() : "";
        if (!category.isEmpty() && !round.isEmpty()) {
            return category + " • " + round;
        }
        if (!category.isEmpty()) {
            return category;
        }
        if (!round.isEmpty()) {
            return round;
        }
        return "";
    }
}
