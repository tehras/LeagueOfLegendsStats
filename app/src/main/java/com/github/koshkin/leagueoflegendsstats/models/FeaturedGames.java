package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tehras on 1/21/16.
 */
public class FeaturedGames implements Request.ParserCallback<FeaturedGames> {

    @SerializedName("gameList")
    private ArrayList<ObservableGame> mObservableGames;

    public ArrayList<ObservableGame> getObservableGames() {
        return mObservableGames;
    }

    @Override
    public FeaturedGames parse(String body) {
        return new Gson().fromJson(body, FeaturedGames.class);
    }
}
