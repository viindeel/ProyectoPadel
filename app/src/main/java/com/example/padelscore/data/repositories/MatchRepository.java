package com.example.padelscore.data.repositories;

import com.example.padelscore.model.Match;

import java.util.List;

public interface MatchRepository {

    interface MatchesCallback {
        void onResponse(List<Match> matches);
        void onFailure(Throwable t);
    }

    void getMatches(String tournamentId, MatchesCallback callback);

    void getMatchesByDate(String date, MatchesCallback callback);

    void getFipMatchesByDate(String date, MatchesCallback callback);
}

