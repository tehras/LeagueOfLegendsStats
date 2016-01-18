package com.github.koshkin.leagueoflegendsstats.models.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ban {

    @SerializedName("championId")
    @Expose
    private int championId;
    @SerializedName("pickTurn")
    @Expose
    private int pickTurn;

    /**
     * @return The championId
     */
    public int getChampionId() {
        return championId;
    }

    /**
     * @param championId The championId
     */
    public void setChampionId(int championId) {
        this.championId = championId;
    }

    /**
     * @return The pickTurn
     */
    public int getPickTurn() {
        return pickTurn;
    }

    /**
     * @param pickTurn The pickTurn
     */
    public void setPickTurn(int pickTurn) {
        this.pickTurn = pickTurn;
    }

}
