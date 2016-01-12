package com.github.koshkin.leagueoflegendsstats.models;

import com.github.koshkin.leagueoflegendsstats.networking.Response;

/**
 * Created by tehras on 1/10/16.
 */
public class SummonerAggregateObject {

    private Summoner mSummoner;
    private PlayerStatSummaries mPlayerStatSummaries;
    private Response.Status mStatus;

    public Response.Status getStatus() {
        return mStatus;
    }

    public void setStatus(Response.Status status) {
        mStatus = status;
    }

    public SummonerAggregateObject() {
    }

    public SummonerAggregateObject(Summoner summoner) {
        mSummoner = summoner;
    }

    public Summoner getSummoner() {
        return mSummoner;
    }

    public void setSummoner(Summoner summoner) {
        mSummoner = summoner;
    }

    public PlayerStatSummaries getPlayerStatSummaries() {
        return mPlayerStatSummaries;
    }

    public void setPlayerStatSummaries(PlayerStatSummaries playerStatSummaries) {
        mPlayerStatSummaries = playerStatSummaries;
    }
}
