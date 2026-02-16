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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
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

    public void loadTournamentDetail(String id) {
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

    public void loadMatches(String tournamentId) {
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

    public void refreshMatches(String tournamentId) {
        loadMatches(tournamentId);
    }

    public void loadMatchesByDate(String date) {
        isLoading.setValue(true);
        matchRepository.getMatchesByDate(date, new MatchRepository.MatchesCallback() {
            @Override
            public void onResponse(List<Match> matchList) {
                matches.postValue(matchList);
                error.postValue(null);
                isLoading.postValue(false);
            }

            @Override
            public void onFailure(Throwable t) {
                error.postValue("Error al cargar partidos de hoy: " + t.getMessage());
                isLoading.postValue(false);
            }
        });
    }

    public void loadMatchesByDate(String date, Set<String> favoriteIds) {
        isLoading.setValue(true);
        Set<String> favorites = favoriteIds != null ? favoriteIds : new HashSet<>();
        List<Match> combined = new ArrayList<>();
        AtomicInteger pending = new AtomicInteger(2);

        MatchRepository.MatchesCallback callback = new MatchRepository.MatchesCallback() {
            @Override
            public void onResponse(List<Match> matchList) {
                if (matchList != null) {
                    combined.addAll(matchList);
                }
                if (pending.decrementAndGet() == 0) {
                    matches.postValue(sortFavoritesFirst(combined, favorites));
                    error.postValue(null);
                    isLoading.postValue(false);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (pending.decrementAndGet() == 0) {
                    matches.postValue(sortFavoritesFirst(combined, favorites));
                    error.postValue("Error al cargar partidos de hoy: " + t.getMessage());
                    isLoading.postValue(false);
                }
            }
        };

        matchRepository.getMatchesByDate(date, callback);
        matchRepository.getFipMatchesByDate(date, callback);
    }

    private List<Match> sortFavoritesFirst(List<Match> matches, Set<String> favorites) {
        if (favorites == null || favorites.isEmpty()) {
            return matches;
        }
        List<Match> favoritesFirst = new ArrayList<>();
        List<Match> others = new ArrayList<>();
        for (Match match : matches) {
            String tournamentId = match.getTournamentId();
            if (tournamentId != null && favorites.contains(tournamentId)) {
                favoritesFirst.add(match);
            } else {
                others.add(match);
            }
        }
        favoritesFirst.addAll(others);
        return favoritesFirst;
    }
}
