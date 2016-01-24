
package com.github.koshkin.leagueoflegendsstats.models.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rune {

    @SerializedName("runeId")
    @Expose
    private long runeId;
    @SerializedName("rank")
    @Expose
    private int rank;
    @SerializedName("count")
    @Expose
    private int count;

    public void setRuneId(long runeId) {
        this.runeId = runeId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return The runeId
     */
    public long getRuneId() {
        return runeId;
    }

    /**
     * @param runeId The runeId
     */
    public void setRuneId(int runeId) {
        this.runeId = runeId;
    }

    /**
     * @return The rank
     */
    public long getRank() {
        return rank;
    }

    /**
     * @param rank The rank
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

}
