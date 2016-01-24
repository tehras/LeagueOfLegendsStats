package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/23/16.
 */
public class RuneForIcons {

    @SerializedName("isrune")
    private boolean isRune;
    @SerializedName("tier")
    private int tier;
    @SerializedName("type")
    private String type;

    public boolean isRune() {
        return isRune;
    }

    public void setIsRune(boolean isRune) {
        this.isRune = isRune;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
