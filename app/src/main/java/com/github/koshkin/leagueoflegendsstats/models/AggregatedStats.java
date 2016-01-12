package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/10/16.
 */
public class AggregatedStats {

    @SerializedName("totalNeutralMinionsKilled")
    private int mTotalNeutralMinionsKilled;
    @SerializedName("totalMinionKills")
    private int mTotalMinionKills;
    @SerializedName("totalChampionKills")
    private int mTotalChampionKills;
    @SerializedName("totalAssists")
    private int mTotalAssists;
    @SerializedName("totalTurretsKilled")
    private int mTotalTurretsKilled;

    public int getTotalNeutralMinionsKilled() {
        return mTotalNeutralMinionsKilled;
    }

    public void setTotalNeutralMinionsKilled(int totalNeutralMinionsKilled) {
        mTotalNeutralMinionsKilled = totalNeutralMinionsKilled;
    }

    public int getTotalMinionKills() {
        return mTotalMinionKills;
    }

    public void setTotalMinionKills(int totalMinionKills) {
        mTotalMinionKills = totalMinionKills;
    }

    public int getTotalChampionKills() {
        return mTotalChampionKills;
    }

    public void setTotalChampionKills(int totalChampionKills) {
        mTotalChampionKills = totalChampionKills;
    }

    public int getTotalAssists() {
        return mTotalAssists;
    }

    public void setTotalAssists(int totalAssists) {
        mTotalAssists = totalAssists;
    }

    public int getTotalTurretsKilled() {
        return mTotalTurretsKilled;
    }

    public void setTotalTurretsKilled(int totalTurretsKilled) {
        mTotalTurretsKilled = totalTurretsKilled;
    }
}
