package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/12/16.
 */
public class Match {

    @SerializedName("matchId")
    private long mMatchId;
    @SerializedName("champion")
    private int mChampionKey;
    @SerializedName("queue")
    private GameQueue mGameQueue;
    @SerializedName("lane")
    private Lane mLane;
    @SerializedName("season")
    private Season mSeason;
    @SerializedName("timestamp")
    private long mTimestamp;

    public long getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(long timestamp) {
        mTimestamp = timestamp;
    }

    public long getMatchId() {
        return mMatchId;
    }

    public void setMatchId(long matchId) {
        mMatchId = matchId;
    }

    public int getChampionKey() {
        return mChampionKey;
    }

    public void setChampionKey(int championKey) {
        mChampionKey = championKey;
    }

    public GameQueue getGameQueue() {
        return mGameQueue;
    }

    public void setGameQueue(GameQueue gameQueue) {
        mGameQueue = gameQueue;
    }

    public Lane getLane() {
        return mLane;
    }

    public void setLane(Lane lane) {
        mLane = lane;
    }

    public Season getSeason() {
        return mSeason;
    }

    public void setSeason(Season season) {
        mSeason = season;
    }

    public enum GameQueue {
        RANKED_SOLO_5x5, RANKED_TEAM_3x3, RANKED_TEAM_5x5
    }

    public enum Season {
        PRESEASON3, SEASON3, PRESEASON2014, SEASON2014, PRESEASON2015, SEASON2015, PRESEASON2016, SEASON2016
    }

    public enum Lane {
        MID, MIDDLE, TOP, JUNGLE, BOT, BOTTOM
    }
}
