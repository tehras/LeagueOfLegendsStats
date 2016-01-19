package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by tehras on 1/18/16.
 */
public class Favorite {

    @SerializedName("summonerId")
    private String mSummonerId;
    @SerializedName("date")
    private long mDate;
    @SerializedName("wins")
    private int mWins = -1;
    @SerializedName("losses")
    private int mLosses = -1;
    @SerializedName("name")
    private String mName;
    @SerializedName("iconId")
    private int mIconId = -1;

    public Favorite(PlayerRanked rankedStats, String summonerId, String summonerName, int summonerIconId) {
        mSummonerId = summonerId;
        mName = summonerName;
        mIconId = summonerIconId;

        populateRankedWInsLossesAssists(rankedStats.getChampions());

        mDate = Calendar.getInstance().getTimeInMillis();
    }

    private void populateRankedWInsLossesAssists(ArrayList<Champion> champions) {
        int wins = 0;
        int losses = 0;

        if (champions != null && champions.size() > 0) {
            for (Champion champion : champions) {
                if (champion.getStats() == null)
                    continue;
                wins = wins + champion.getStats().getTotalSessionsWon();
                losses = losses + champion.getStats().getTotalSessionsLost();
            }
        }

        if (wins > 0)
            mWins = wins;
        if (losses > 0)
            mLosses = losses;

        mDate = Calendar.getInstance().getTimeInMillis();
    }

    public int getIconId() {
        return mIconId;
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
    }

    public int getWins() {
        return mWins;
    }

    public void setWins(int wins) {
        mWins = wins;
    }

    public int getLosses() {
        return mLosses;
    }

    public void setLosses(int losses) {
        mLosses = losses;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Favorite(SummonerAggregateObject combinedObj) {
        Summoner summoner = combinedObj.getSummoner();
        PlayerStatSummaries playerStats = combinedObj.getPlayerStatSummaries();
        mSummonerId = summoner.getSummonerId();
        mIconId = summoner.getSummonerInfo().getProfileIconId();
        mName = summoner.getSummonerInfo().getName();
        populateRankedWinsLossesAssists(playerStats);

        mDate = Calendar.getInstance().getTimeInMillis();
    }

    private void populateRankedWinsLossesAssists(PlayerStatSummaries playerStats) {
        if (playerStats != null) {
            ArrayList<PlayerSummary> playerSumaries = playerStats.getPlayerSummaries();
            for (PlayerSummary playerSummary : playerSumaries) {
                if (playerSummary.getSummaryType() == PlayerSummary.SummaryType.RANKED_SOLO_5X) {
                    mWins = playerSummary.getWins();
                    mLosses = playerSummary.getLosses();
                    break;
                }
            }
        }
    }

    public static Favorite fromJson(String string) {
        return new Gson().fromJson(string, Favorite.class);
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    public String getSummonerId() {
        return mSummonerId;
    }

    public void setSummonerId(String summonerId) {
        mSummonerId = summonerId;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }
}
