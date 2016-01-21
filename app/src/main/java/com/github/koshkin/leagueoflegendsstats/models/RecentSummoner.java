package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by tehras on 1/18/16.
 * <p/>
 * Simple Summoner
 */
public class RecentSummoner extends SugarRecord {

    @SerializedName("summonerId")
    protected String summonerId;
    @SerializedName("date")
    protected long date;
    @SerializedName("wins")
    protected int wins = -1;
    @SerializedName("losses")
    protected int losses = -1;
    @SerializedName("name")
    protected String name;
    @SerializedName("iconId")
    protected int iconId = -1;

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }


    public RecentSummoner() {
    }

//    public RecentSummoner(PlayerRanked rankedStats, String summonerId, String summonerName, int summonerIconId) {
//        this.summonerId = summonerId;
//        this.name = summonerName;
//        this.iconId = summonerIconId;
//
//        populateRankedWInsLossesAssists(rankedStats.getChampions());
//
//        this.date = Calendar.getInstance().getTimeInMillis();
//    }

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
            this.wins = wins;
        if (losses > 0)
            this.losses = losses;

        this.date = Calendar.getInstance().getTimeInMillis();
    }
//
//    public RecentSummoner(Summoner summoner){
//        setName(summoner.getSummonerInfo().getName());
//        setSummonerId(summoner.getSummonerId());
//    }

    public RecentSummoner(SummonerAggregateObject combinedObj) {
        Summoner summoner = combinedObj.getSummoner();
        PlayerStatSummaries playerStats = combinedObj.getPlayerStatSummaries();
        this.summonerId = summoner.getSummonerId();
        this.iconId = summoner.getSummonerInfo().getProfileIconId();
        this.name = summoner.getSummonerInfo().getName();
        populateRankedWinsLossesAssists(playerStats);

        this.date = Calendar.getInstance().getTimeInMillis();
    }

    private void populateRankedWinsLossesAssists(PlayerStatSummaries playerStats) {
        if (playerStats != null) {
            ArrayList<PlayerSummary> playerSumaries = playerStats.getPlayerSummaries();
            for (PlayerSummary playerSummary : playerSumaries) {
                if (playerSummary.getSummaryType() == PlayerSummary.SummaryType.RANKED_SOLO_5X) {
                    this.wins = playerSummary.getWins();
                    this.losses = playerSummary.getLosses();
                    break;
                }
            }
        }
    }

    public void updateFavorite(RecentSummoner simpleSummoner) {
        setDate(simpleSummoner.getDate());
        setIconId(simpleSummoner.getIconId());
        setLosses(simpleSummoner.getLosses());
        setWins(simpleSummoner.getWins());
        setName(simpleSummoner.getName());
        setSummonerId(simpleSummoner.getSummonerId());
    }
}
