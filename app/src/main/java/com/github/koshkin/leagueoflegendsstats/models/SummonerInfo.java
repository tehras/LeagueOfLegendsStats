package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tehras on 1/10/16.
 * <p/>
 * SummonerInfo
 */
public class SummonerInfo {

    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("profileIconId")
    private int mProfileIconId;
    @SerializedName("revisionDate")
    private double mRevisionDate;
    @SerializedName("summonerLevel")
    private int mSummonerLevel;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getProfileIconId() {
        return mProfileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        mProfileIconId = profileIconId;
    }

    public double getRevisionDate() {
        return mRevisionDate;
    }

    public void setRevisionDate(double revisionDate) {
        mRevisionDate = revisionDate;
    }

    public int getSummonerLevel() {
        return mSummonerLevel;
    }

    public void setSummonerLevel(int summonerLevel) {
        mSummonerLevel = summonerLevel;
    }
}
