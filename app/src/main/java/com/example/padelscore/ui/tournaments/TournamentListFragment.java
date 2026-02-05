package com.example.padelscore.ui.tournaments;

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

public class TournamentListFragment extends Fragment {

    private TournamentViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tournament_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(TournamentViewModel.class);
        RecyclerView recyclerView = view.findViewById(R.id.tournaments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel.getTournaments().observe(getViewLifecycleOwner(), tournaments -> {
            recyclerView.setAdapter(new TournamentAdapter(tournaments, viewModel));
        });
    }
}
