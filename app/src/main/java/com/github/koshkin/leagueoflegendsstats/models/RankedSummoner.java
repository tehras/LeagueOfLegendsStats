package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

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

    private Summoner mSummoner;
    private int mRank;

    public Summoner getSummoner() {
        return mSummoner;
    }

    public void setSummoner(Summoner summoner) {
        mSummoner = summoner;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RankedSummoner summoner = (RankedSummoner) o;

        return mPlayerOrTeamId != null ? mPlayerOrTeamId.equals(summoner.mPlayerOrTeamId) : summoner.mPlayerOrTeamId == null && (mPlayerOrTeamName != null ? mPlayerOrTeamName.equals(summoner.mPlayerOrTeamName) : summoner.mPlayerOrTeamName == null);

    }

    @Override
    public int hashCode() {
        int result = mPlayerOrTeamId != null ? mPlayerOrTeamId.hashCode() : 0;
        result = 31 * result + (mPlayerOrTeamName != null ? mPlayerOrTeamName.hashCode() : 0);
        return result;
    }

    public void setRank(int rank) {
        mRank = rank;
    }

    public int getRank() {
        return mRank;
    }

    public class WinComparator implements Comparator<RankedSummoner> {
        @Override
        public int compare(RankedSummoner lhs, RankedSummoner rhs) {
            return (int) (rhs.getWins() - lhs.getWins());
        }
    }

    public class LossComparator implements Comparator<RankedSummoner> {
        @Override
        public int compare(RankedSummoner lhs, RankedSummoner rhs) {
            return (int) (rhs.getLosses() - lhs.getLosses());
        }
    }

    public class PointsComparator implements Comparator<RankedSummoner> {
        @Override
        public int compare(RankedSummoner lhs, RankedSummoner rhs) {
            return (int) (rhs.getLeaguePoints() - lhs.getLeaguePoints());
        }
    }

    public class WinPercentageComparator implements Comparator<RankedSummoner> {

        @Override
        public int compare(RankedSummoner lhs, RankedSummoner rhs) {
            return Double.compare(getWinPercentage(rhs), getWinPercentage(lhs));
        }

        private double getWinPercentage(RankedSummoner summoner) {
            return ((double) summoner.getWins() * 100d) / ((double) ((summoner.getWins()) + summoner.getLosses()));
        }
    }

    public enum SortBy {
        WINS, LOSSES, WINP, POINTS
    }
}
