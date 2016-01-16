package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/15/16.
 */
public class RankedSummoner implements Comparable<RankedSummoner> {

    @SerializedName("playerOrTeamId")
    private String mPlayerOrTeamId;
    @SerializedName("playerOrTeamName")
    private String mPlayerOrTeamName;
    @SerializedName("division")
    private String mDivision;
    @SerializedName("leaguePoints")
    private long mLeaguePoints;
    @SerializedName("wins")
    private long mWins;
    @SerializedName("losses")
    private long mLosses;
    @SerializedName("isHotStreak")
    private boolean mIsHotStreak;
    @SerializedName("isVeteran")
    private boolean mIsVeteran;
    @SerializedName("isFreshBlood")
    private boolean mIsFreshBlood;
    @SerializedName("isInactive")
    private boolean mIsInactive;

    public String getPlayerOrTeamId() {
        return mPlayerOrTeamId;
    }

    public void setPlayerOrTeamId(String playerOrTeamId) {
        mPlayerOrTeamId = playerOrTeamId;
    }

    public String getPlayerOrTeamName() {
        return mPlayerOrTeamName;
    }

    public void setPlayerOrTeamName(String playerOrTeamName) {
        mPlayerOrTeamName = playerOrTeamName;
    }

    public String getDivision() {
        return mDivision;
    }

    public void setDivision(String division) {
        mDivision = division;
    }

    public long getLeaguePoints() {
        return mLeaguePoints;
    }

    public void setLeaguePoints(long leaguePoints) {
        mLeaguePoints = leaguePoints;
    }

    public long getWins() {
        return mWins;
    }

    public void setWins(long wins) {
        mWins = wins;
    }

    public long getLosses() {
        return mLosses;
    }

    public void setLosses(long losses) {
        mLosses = losses;
    }

    public boolean isHotStreak() {
        return mIsHotStreak;
    }

    public void setHotStreak(boolean hotStreak) {
        mIsHotStreak = hotStreak;
    }

    public boolean isVeteran() {
        return mIsVeteran;
    }

    public void setVeteran(boolean veteran) {
        mIsVeteran = veteran;
    }

    public boolean isFreshBlood() {
        return mIsFreshBlood;
    }

    public void setFreshBlood(boolean freshBlood) {
        mIsFreshBlood = freshBlood;
    }

    public boolean isInactive() {
        return mIsInactive;
    }

    public void setInactive(boolean inactive) {
        mIsInactive = inactive;
    }

    @Override
    public int compareTo(RankedSummoner another) {
        return (int) (another.getLeaguePoints() - this.getLeaguePoints());
    }
}
