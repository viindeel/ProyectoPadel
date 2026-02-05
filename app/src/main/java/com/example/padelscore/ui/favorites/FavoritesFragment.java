package com.example.padelscore.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.padelscore.R;
import com.example.padelscore.model.Tournament;
import com.example.padelscore.ui.tournaments.TournamentViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private TournamentViewModel viewModel;
    private RecyclerView recyclerView;
    private FavoritesAdapter adapter;
    private List<Tournament> favoriteTournaments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.favorites_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new FavoritesAdapter(favoriteTournaments);
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(requireActivity()).get(TournamentViewModel.class);
        viewModel.getTournaments().observe(getViewLifecycleOwner(), tournaments -> {
            favoriteTournaments.clear();
            for (Tournament tournament : tournaments) {
                if (tournament.isFavorite()) {
                    favoriteTournaments.add(tournament);
                }
            }
            adapter.notifyDataSetChanged();
        });
    }
}
