package com.github.koshkin.leagueoflegendsstats.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/10/16.
 * <p/>
 * Champions played
 */
public class Champion implements Comparable<Champion> {

    @SerializedName("id")
    private int mId;
    @SerializedName("stats")
    private Stats mStats;

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

        return anotherSessionsPlayed - thisSessionsPlayed;
    }
}
