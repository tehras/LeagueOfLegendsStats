package com.github.koshkin.leagueoflegendsstats.models.match;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/18/16.
 */
public class BaseTimeLineDelta {

    public static float DEFAULT_VALUE = -20000f;

    @SerializedName("zeroToTen")
    @Expose
    private float zeroToTen = DEFAULT_VALUE;
    @SerializedName("tenToTwenty")
    @Expose
    private float tenToTwenty = DEFAULT_VALUE;
    @SerializedName("twentyToThirty")
    @Expose
    private float twentyToThirty = DEFAULT_VALUE;
    @SerializedName("thirtyToEnd")
    @Expose
    private float thirtyToEnd = DEFAULT_VALUE;

    public float getZeroToTen() {
        return zeroToTen;
    }

    public void setZeroToTen(float zeroToTen) {
        this.zeroToTen = zeroToTen;
    }

    public float getTenToTwenty() {
        return tenToTwenty;
    }

    public void setTenToTwenty(float tenToTwenty) {
        this.tenToTwenty = tenToTwenty;
    }

    public float getTwentyToThirty() {
        return twentyToThirty;
    }

    public void setTwentyToThirty(float twentyToThirty) {
        this.twentyToThirty = twentyToThirty;
    }

    public float getThirtyToEnd() {
        return thirtyToEnd;
    }

    public void setThirtyToEnd(float thirtyToEnd) {
        this.thirtyToEnd = thirtyToEnd;
    }
}
