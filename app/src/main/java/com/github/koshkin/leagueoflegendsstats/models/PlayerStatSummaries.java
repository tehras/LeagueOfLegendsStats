package com.github.koshkin.leagueoflegendsstats.models;


import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tehras on 1/10/16.
 */
public class PlayerStatSummaries implements Request.ParserCallback<PlayerStatSummaries> {

    @SerializedName("playerStatSummaries")
    private ArrayList<PlayerSummary> mPlayerSummaries;

    public ArrayList<PlayerSummary> getPlayerSummaries() {
        return mPlayerSummaries;
    }

    public void setPlayerSummaries(ArrayList<PlayerSummary> playerSummaries) {
        mPlayerSummaries = playerSummaries;
    }

    @Override
    public PlayerStatSummaries parse(String body) {
        return new Gson().fromJson(body, PlayerStatSummaries.class);
    }

    @Override
    public String toString() {
        return "PlayerStatSummaries{" +
                "mPlayerSummaries=" + mPlayerSummaries +
                "}";
    }
}
