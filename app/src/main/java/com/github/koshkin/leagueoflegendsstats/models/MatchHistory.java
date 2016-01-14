package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tehras on 1/12/16.
 */
public class MatchHistory implements Request.ParserCallback {

    @SerializedName("matches")
    private ArrayList<Match> mMatches;

    @SerializedName("totalGames")
    private int mTotalGames;

    public ArrayList<Match> getMatches() {
        return mMatches;
    }

    public void setMatches(ArrayList<Match> matches) {
        mMatches = matches;
    }

    public int getTotalGames() {
        return mTotalGames;
    }

    public void setTotalGames(int totalGames) {
        mTotalGames = totalGames;
    }

    @Override
    public Object parse(String body) {
        return new Gson().fromJson(body, MatchHistory.class);
    }
}
