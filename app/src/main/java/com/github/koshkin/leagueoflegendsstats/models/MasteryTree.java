package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/23/16.
 */
public class MasteryTree {

    @SerializedName("masteryId")
    private long masteryId;
    @SerializedName("prereq")
    private long prereq;

    public long getMasteryId() {
        return masteryId;
    }

    public void setMasteryId(long masteryId) {
        this.masteryId = masteryId;
    }

    public long getPrereq() {
        return prereq;
    }

    public void setPrereq(long prereq) {
        this.prereq = prereq;
    }
}
