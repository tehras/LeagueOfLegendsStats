package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tehras on 1/12/16.
 */
public class RecentGames implements Request.ParserCallback<RecentGames> {

    @SerializedName("games")
    private ArrayList<Game> mGames;

    public ArrayList<Game> getGames() {
        return mGames;
    }

    public void setGames(ArrayList<Game> games) {
        mGames = games;
    }

    @Override
    public RecentGames parse(String body) {
        return new Gson().fromJson(body, RecentGames.class);
    }
}
