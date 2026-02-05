package com.example.padelscore.ui.tournaments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.padelscore.data.repositories.TournamentRepository;
import com.example.padelscore.data.repositories.TournamentRepositoryImpl;
import com.example.padelscore.model.Tournament;

import java.util.List;

public class TournamentViewModel extends ViewModel {

    private final TournamentRepository repository = new TournamentRepositoryImpl();
    private final MutableLiveData<List<Tournament>> tournaments = new MutableLiveData<>();
    private final MutableLiveData<Tournament> selected = new MutableLiveData<>();

    public LiveData<List<Tournament>> getTournaments() {
        loadTournaments();
        return tournaments;
    }

    public void select(Tournament tournament) {
        selected.setValue(tournament);
    }

    public LiveData<Tournament> getSelected() {
        return selected;
    }

    private void loadTournaments() {
        repository.getTournaments(new TournamentRepository.TournamentsCallback() {
            @Override
            public void onResponse(List<Tournament> tournamentList) {
                tournaments.postValue(tournamentList);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
