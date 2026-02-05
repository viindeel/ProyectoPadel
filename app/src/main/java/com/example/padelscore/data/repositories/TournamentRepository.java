package com.example.padelscore.data.repositories;

import com.example.padelscore.model.Tournament;

import java.util.List;

public interface TournamentRepository {

    interface TournamentsCallback {
        void onResponse(List<Tournament> tournaments);
        void onFailure(Throwable t);
    }

    void getTournaments(TournamentsCallback callback);
}
