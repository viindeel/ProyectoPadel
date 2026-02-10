package com.example.padelscore.ui.tournaments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.padelscore.data.repositories.MatchRepository;
import com.example.padelscore.data.repositories.MatchRepositoryImpl;
import com.example.padelscore.data.repositories.TournamentRepository;
import com.example.padelscore.data.repositories.TournamentRepositoryImpl;
import com.example.padelscore.model.Match;
import com.example.padelscore.model.Tournament;

import java.util.List;
import java.util.stream.Collectors;

public class TournamentViewModel extends ViewModel {

    private final TournamentRepository repository = new TournamentRepositoryImpl();
    private final MatchRepository matchRepository = new MatchRepositoryImpl();
    private final MutableLiveData<List<Tournament>> tournaments = new MutableLiveData<>();
    private final MutableLiveData<Tournament> selected = new MutableLiveData<>();
    private final MutableLiveData<List<Match>> matches = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

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

    public LiveData<List<Tournament>> getFavorites() {
        return new MutableLiveData<List<Tournament>>() {
            @Override
            protected void onActive() {
                super.onActive();
                List<Tournament> favs = tournaments.getValue() != null
                    ? tournaments.getValue().stream()
                        .filter(Tournament::isFavorite)
                        .collect(Collectors.toList())
                    : List.of();
                setValue(favs);
            }
        };
    }

    public LiveData<List<Match>> getMatches() {
        return matches;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void clearError() {
        error.setValue(null);
    }

    private void loadTournaments() {
        if (tournaments.getValue() != null && !tournaments.getValue().isEmpty()) {
            return;
        }

        isLoading.setValue(true);
        repository.getTournaments(new TournamentRepository.TournamentsCallback() {
            @Override
            public void onResponse(List<Tournament> tournamentList) {
                tournaments.postValue(tournamentList);
                error.postValue(null);
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(Throwable t) {
                error.postValue("Error al cargar torneos: " + t.getMessage());
                isLoading.postValue(false);
                t.printStackTrace();
            }
        });
    }

    public void refreshTournaments() {
        isLoading.setValue(true);
        repository.getTournaments(new TournamentRepository.TournamentsCallback() {
            @Override
            public void onResponse(List<Tournament> tournamentList) {
                tournaments.postValue(tournamentList);
                error.postValue(null);
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(Throwable t) {
                error.postValue("Error al refrescar torneos: " + t.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    public void loadTournamentDetail(long id) {
        isLoading.setValue(true);
        repository.getTournamentDetail(id, new TournamentRepository.TournamentDetailCallback() {
            @Override
            public void onResponse(Tournament tournament) {
                selected.postValue(tournament);
                error.postValue(null);
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(Throwable t) {
                error.postValue("Error al cargar detalle: " + t.getMessage());
                isLoading.postValue(false);
                t.printStackTrace();
            }
        });
    }

    public void loadMatches(long tournamentId) {
        isLoading.setValue(true);
        matchRepository.getMatches(tournamentId, new MatchRepository.MatchesCallback() {
            @Override
            public void onResponse(List<Match> matchList) {
                matches.postValue(matchList);
                error.postValue(null);
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(Throwable t) {
                error.postValue("Error al cargar partidos: " + t.getMessage());
                isLoading.postValue(false);
                t.printStackTrace();
            }
        });
    }

    public void refreshMatches(long tournamentId) {
        loadMatches(tournamentId);
    }
}
