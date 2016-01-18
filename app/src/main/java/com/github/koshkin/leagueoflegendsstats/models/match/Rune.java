
package com.github.koshkin.leagueoflegendsstats.models.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rune {

    @SerializedName("runeId")
    @Expose
    private int runeId;
    @SerializedName("rank")
    @Expose
    private int rank;

    /**
     * @return The runeId
     */
    public int getRuneId() {
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
