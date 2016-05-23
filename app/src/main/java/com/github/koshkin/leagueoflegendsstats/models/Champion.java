package com.github.koshkin.leagueoflegendsstats.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
 * Created by tehras on 1/10/16.
 * <p/>
 * Champions played
 */
public class Champion implements Comparable<Champion> {

    @SerializedName("id")
    private int mId;
    @SerializedName("championId")
    private int mChampionId;
    @SerializedName("stats")
    private Stats mStats;
    @SerializedName("pickTurn")
    private int mPickTurn;
    @SerializedName("teamId")
    private TeamSide mTeamSide;

    public TeamSide getTeamSide() {
        return mTeamSide;
    }

    public void setTeamSide(TeamSide teamSide) {
        mTeamSide = teamSide;
    }

    public int getChampionId() {
        return mChampionId;
    }

    public void setChampionId(int championId) {
        mChampionId = championId;
    }

    public int getPickTurn() {
        return mPickTurn;
    }

    public void setPickTurn(int pickTurn) {
        mPickTurn = pickTurn;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public Stats getStats() {
        return mStats;
    }

    public void setStats(Stats stats) {
        mStats = stats;
    }

    @Override
    public int compareTo(@NonNull Champion another) {
        Stats thisStats = this.getStats();
        Stats anotherStats = another.getStats();

        if (thisStats == null || anotherStats == null) {
            if (thisStats == null && anotherStats == null)
                return 0;
            else if (thisStats != null)
                return 1;
            else
                return -1;
        }

        int thisSessionsPlayed = thisStats.getTotalSessionsPlayed();
        int anotherSessionsPlayed = anotherStats.getTotalSessionsPlayed();

        int sessionsPlayedDiff = anotherSessionsPlayed - thisSessionsPlayed;
        if (sessionsPlayedDiff != 0)
            return sessionsPlayedDiff;

        int thisId = this.getId();
        int anotherId = another.getId();

        return thisId - anotherId;
    }

    public class Wins implements Comparator<Champion> {

        @Override
        public int compare(Champion lhs, Champion rhs) {
            Stats thisStats = lhs.getStats();
            Stats anotherStats = rhs.getStats();

            if (thisStats == null || anotherStats == null) {
                if (thisStats == null && anotherStats == null)
                    return 0;
                else if (thisStats != null)
                    return 1;
                else
                    return -1;
            }

            int thisSessionsPlayed = thisStats.getTotalSessionsWon();
            int anotherSessionsPlayed = anotherStats.getTotalSessionsWon();

            return anotherSessionsPlayed - thisSessionsPlayed;
        }
    }

    public class Losses implements Comparator<Champion> {

        @Override
        public int compare(Champion lhs, Champion rhs) {
            Stats thisStats = lhs.getStats();
            Stats anotherStats = rhs.getStats();

            if (thisStats == null || anotherStats == null) {
                if (thisStats == null && anotherStats == null)
                    return 0;
                else if (thisStats != null)
                    return 1;
                else
                    return -1;
            }

            int thisSessionsPlayed = thisStats.getTotalSessionsLost();
            int anotherSessionsPlayed = anotherStats.getTotalSessionsLost();

            return anotherSessionsPlayed - thisSessionsPlayed;
        }
    }

    public class Games implements Comparator<Champion> {

        @Override
        public int compare(Champion lhs, Champion rhs) {
            Stats thisStats = lhs.getStats();
            Stats anotherStats = rhs.getStats();

            if (thisStats == null || anotherStats == null) {
                if (thisStats == null && anotherStats == null)
                    return 0;
                else if (thisStats != null)
                    return 1;
                else
                    return -1;
            }

            int thisSessionsPlayed = thisStats.getTotalSessionsPlayed();
            int anotherSessionsPlayed = anotherStats.getTotalSessionsPlayed();

            return anotherSessionsPlayed - thisSessionsPlayed;
        }
    }

    public class WinP implements Comparator<Champion> {

        @Override
        public int compare(Champion lhs, Champion rhs) {
            Stats thisStats = lhs.getStats();
            Stats anotherStats = rhs.getStats();

            if (thisStats == null || anotherStats == null) {
                if (thisStats == null && anotherStats == null)
                    return 0;
                else if (thisStats != null)
                    return 1;
                else
                    return -1;
            }

            double thisWins = thisStats.getTotalSessionsWon();
            double anotherWins = anotherStats.getTotalSessionsWon();
            double thisSessionsPlayed = thisStats.getTotalSessionsPlayed();
            double anotherSessionsPlayed = anotherStats.getTotalSessionsPlayed();

            return Double.compare((anotherWins * 100d / anotherSessionsPlayed), (thisWins * 100d / thisSessionsPlayed));
        }
    }

    public enum SortType {
        WINS, GAMES_PLAYED, WINP, LOSSES;
    }
}
