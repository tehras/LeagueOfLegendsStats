package com.github.koshkin.leagueoflegendsstats.models.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mastery {

    @SerializedName("masteryId")
    @Expose
    private long masteryId;
    @SerializedName("rank")
    @Expose
    private int rank;

    /**
     * @return The masteryId
     */
    public long getMasteryId() {
        return masteryId;
    }

    /**
     * @param masteryId The masteryId
     */
    public void setMasteryId(long masteryId) {
        this.masteryId = masteryId;
    }

    /**
     * @return The rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * @param rank The rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

}
