package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tehras on 1/10/16.
 */
public class PlayerRanked implements Request.ParserCallback<PlayerRanked> {

    @SerializedName("champions")
    private ArrayList<Champion> mChampions;
    @SerializedName("summonerId")
    private int mSummonerId;
    private double mKills = -1d, mDeaths = -1d, mAssists = -1d;
    private double mKDA = -1d;

    public double getKills() {
        return mKills;
    }

    public void setKills(double kills) {
        mKills = kills;
    }

    public double getDeaths() {
        return mDeaths;
    }

    public void setDeaths(double deaths) {
        mDeaths = deaths;
    }

    public double getAssists() {
        return mAssists;
    }

    public void setAssists(double assists) {
        mAssists = assists;
    }

    public double getKDA() {
        return mKDA;
    }

    public void setKDA(double KDA) {
        mKDA = KDA;
    }

    public ArrayList<Champion> getChampions() {
        return mChampions;
    }

    public void setChampions(ArrayList<Champion> champions) {
        mChampions = champions;
    }

    public int getSummonerId() {
        return mSummonerId;
    }

    public void setSummonerId(int summonerId) {
        mSummonerId = summonerId;
    }

    @Override
    public PlayerRanked parse(String body) {
        return new Gson().fromJson(body, PlayerRanked.class);
    }
}
