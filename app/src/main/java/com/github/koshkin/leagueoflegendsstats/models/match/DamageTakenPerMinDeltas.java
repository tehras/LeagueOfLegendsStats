package com.github.koshkin.leagueoflegendsstats.models.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DamageTakenPerMinDeltas {

    @SerializedName("zeroToTen")
    @Expose
    private float zeroToTen;
    @SerializedName("tenToTwenty")
    @Expose
    private float tenToTwenty;

    /**
     * @return The zeroToTen
     */
    public float getZeroToTen() {
        return zeroToTen;
    }

    /**
     * @param zeroToTen The zeroToTen
     */
    public void setZeroToTen(float zeroToTen) {
        this.zeroToTen = zeroToTen;
    }

    /**
     * @return The tenToTwenty
     */
    public float getTenToTwenty() {
        return tenToTwenty;
    }

    /**
     * @param tenToTwenty The tenToTwenty
     */
    public void setTenToTwenty(float tenToTwenty) {
        this.tenToTwenty = tenToTwenty;
    }

}
