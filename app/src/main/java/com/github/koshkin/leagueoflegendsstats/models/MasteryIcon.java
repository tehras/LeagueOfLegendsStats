package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tehras on 1/23/16.
 */
public class MasteryIcon {

    @SerializedName("id")
    private long mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private ArrayList<String> mDescription;
    @SerializedName("image")
    private Image mImage;
    @SerializedName("rank")
    private int mRank;
    @SerializedName("prereq")
    private long prereq;

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ArrayList<String> getDescription() {
        return mDescription;
    }

    public void setDescription(ArrayList<String> description) {
        mDescription = description;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }

    public int getRank() {
        return mRank;
    }

    public void setRank(int rank) {
        mRank = rank;
    }

    public long getPrereq() {
        return prereq;
    }

    public void setPrereq(long prereq) {
        this.prereq = prereq;
    }
}
