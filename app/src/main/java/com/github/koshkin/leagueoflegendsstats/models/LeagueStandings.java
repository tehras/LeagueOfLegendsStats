package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Request;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tehras on 1/15/16.
 */
public class LeagueStandings implements Request.ParserCallback<LeagueStandings> {

    @SerializedName("name")
    private String mName;
    @SerializedName("tier")
    private Tier mTier;
    @SerializedName("queue")
    private LeagueQueueType mQueueType;
    @SerializedName("entries")
    private ArrayList<RankedSummoner> mEntries;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Tier getTier() {
        return mTier;
    }

    public void setTier(Tier tier) {
        mTier = tier;
    }

    public LeagueQueueType getQueueType() {
        return mQueueType;
    }

    public void setQueueType(LeagueQueueType queueType) {
        mQueueType = queueType;
    }

    public ArrayList<RankedSummoner> getEntries() {
        return mEntries;
    }

    public void setEntries(ArrayList<RankedSummoner> entries) {
        mEntries = entries;
    }

    @Override
    public LeagueStandings parse(String body) {
        return new Gson().fromJson(body, this.getClass());
    }
}
