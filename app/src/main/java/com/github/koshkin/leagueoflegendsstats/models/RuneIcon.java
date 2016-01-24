package com.github.koshkin.leagueoflegendsstats.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tehras on 1/23/16.
 */
public class RuneIcon {

    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("image")
    private Image mImage;
    @SerializedName("rune")
    private RuneForIcons mRune;
    @SerializedName("stats")
    private HashMap<String, Double> mStats;
    @SerializedName("tags")
    private ArrayList<String> mTags;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Image getImage() {
        return mImage;
    }

    public void setImage(Image image) {
        mImage = image;
    }

    public RuneForIcons getRune() {
        return mRune;
    }

    public void setRune(RuneForIcons rune) {
        mRune = rune;
    }

    public HashMap<String, Double> getStats() {
        return mStats;
    }

    public void setStats(HashMap<String, Double> stats) {
        mStats = stats;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }

    public void setTags(ArrayList<String> tags) {
        mTags = tags;
    }
}
